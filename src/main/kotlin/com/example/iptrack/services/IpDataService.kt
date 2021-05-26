package com.example.iptrack.services

import com.example.iptrack.dtos.CountryInfoResponseDto
import com.example.iptrack.dtos.CurrencyDto
import com.example.iptrack.exceptions.UserException
import com.example.iptrack.mappers.ResponseMapper
import com.example.iptrack.repositories.BlackListRepository
import com.example.iptrack.repositories.BlackListRepositorys
import com.example.iptrack.requesters.DataFixerIoRequester
import com.example.iptrack.requesters.IpCountryRequester
import com.example.iptrack.requesters.RestCountriesRequester
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IpDataService {
    @Autowired
    private lateinit var ipCountryRequester: IpCountryRequester

    @Autowired
    private lateinit var restCountriesRequester: RestCountriesRequester

    @Autowired
    private lateinit var dataFixerIoRequester: DataFixerIoRequester

    @Autowired
    private lateinit var responseMapper: ResponseMapper

    @Autowired
    private lateinit var blackListService: BlackListService

    fun getDataFromIp(ip: String): CountryInfoResponseDto {

        validateBlackList(ip)

        var response = CountryInfoResponseDto()
        val ipCountryInfo = ipCountryRequester.getCountryDataFromIp(ip)

        if (ipCountryInfo.countryCode.isNotEmpty()) {
            val generalCountryInfo =
                restCountriesRequester.getDataFromCountryCode(ipCountryInfo.countryCode.toLowerCase())

            val currencyResponse = getCurrencyInfo(generalCountryInfo.currencies.first())

            response = responseMapper.getResponse(ipCountryInfo, generalCountryInfo, currencyResponse)

        }

        return response
    }

    fun validateBlackList(ip: String) {
        if (blackListService.existElement(ip)) {
            throw UserException("Unauthorized")
        }
    }

    fun getCurrencyInfo(currency: CurrencyDto): Double {

        var response = 1.0
        if (currency.code.isNotEmpty()) {
            val currencies = dataFixerIoRequester.getCurrencies()
            response = currencies.rates[currency.code] ?: 1.0
        }

        return response
    }
}