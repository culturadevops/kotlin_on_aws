package com.example.iptrack.mappers

import com.example.iptrack.dtos.Api2CountryResponseDto
import com.example.iptrack.dtos.CountryInfoResponseDto
import com.example.iptrack.dtos.RestCountriesResponseDto
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

@Component
class ResponseMapper {

    fun getResponse(ipCountryInfo: Api2CountryResponseDto, countryInfo: RestCountriesResponseDto, currencyValue: Double): CountryInfoResponseDto {

        val currency = countryInfo.currencies.first()
        val bdOne = BigDecimal("1")
        val currencyBigDecimal = BigDecimal(currencyValue)

        val currencyEquivalent = bdOne.divide(currencyBigDecimal, 10, RoundingMode.HALF_UP)

        return CountryInfoResponseDto().apply {
            countryName = ipCountryInfo.countryName
            alpha2Code =  ipCountryInfo.countryCode
            alpha3Code = ipCountryInfo.countryCode3
            currencyName = currency.name
            currencySymbol = currency.symbol
            euroEquivalence = currencyEquivalent
        }
    }
}