package com.example.iptrack.controllers

import com.example.iptrack.CommonTests
import com.example.iptrack.dtos.*
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class CountryInfoControllerTest : CommonTests() {

    @Test
    fun `get data from the ips with success result`() {
        val api2CountryResponseDto = Api2CountryResponseDto().apply {
            countryCode = "CO"
            countryCode3 = "COL"
            countryName = "Colombia"
            countryEmoji = "co"
        }
        val currencyDto = CurrencyDto().apply {
            code = "COP"
            name = "Colombian Peso"
            symbol = "$"

        }
        val restCountriesResponseDto = RestCountriesResponseDto().apply {
            name = "Colombia"
            currencies = listOf(currencyDto)
        }
        val currencyValue = 2000.0

        val dataFixerIoResponseDto = DataFixerIoResponseDto().apply { 
            rates = hashMapOf(Pair("COP", currencyValue))
        }

        ipCountryRequesterMock.getCountryDataFromIp(api2CountryResponseDto)
        restCountriesRequesterMock.getCountryDataFromIp(restCountriesResponseDto)
        dataFixerIoRequesterMock.getCurrencies(dataFixerIoResponseDto)

        val request = MockMvcRequestBuilders.get("/ip/10.10.10.10").contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.countryName").value(restCountriesResponseDto.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.alpha2Code").value(api2CountryResponseDto.countryCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.alpha3Code").value(api2CountryResponseDto.countryCode3))
            .andExpect(MockMvcResultMatchers.jsonPath("$.currencyName").value(restCountriesResponseDto.currencies.first().name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.currencySymbol").value(restCountriesResponseDto.currencies.first().symbol))
            .andExpect(MockMvcResultMatchers.jsonPath("$.euroEquivalence").value(1/currencyValue))
    }

    @Test
    fun `get data from the ips with failed result by blacklist`() {
        val api2CountryResponseDto = Api2CountryResponseDto().apply {
            countryCode = "CO"
            countryCode3 = "COL"
            countryName = "Colombia"
            countryEmoji = "co"
        }
        val currencyDto = CurrencyDto().apply {
            code = "COP"
            name = "Colombian Peso"
            symbol = "$"

        }
        val restCountriesResponseDto = RestCountriesResponseDto().apply {
            name = "Colombia"
            currencies = listOf(currencyDto)
        }
        val currencyValue = 2000.0
        val ips = arrayOf("10.10.10.10")
        val blackListRequestDto = BlackListRequestDto().apply { this.ips = ips }

        blackListService.updateList(blackListRequestDto)

        val dataFixerIoResponseDto = DataFixerIoResponseDto().apply {
            rates = hashMapOf(Pair("COP", currencyValue))
        }

        ipCountryRequesterMock.getCountryDataFromIp(api2CountryResponseDto)
        restCountriesRequesterMock.getCountryDataFromIp(restCountriesResponseDto)
        dataFixerIoRequesterMock.getCurrencies(dataFixerIoResponseDto)

        val request = MockMvcRequestBuilders.get("/ip/10.10.10.10").contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.error.code").value("Unauthorized"))

        blackListService.deleteFromList(blackListRequestDto)
    }
}