package com.example.iptrack.dtos

import java.math.BigDecimal

class CountryInfoResponseDto {

    var countryName: String = ""

    var alpha2Code: String = ""

    var alpha3Code: String = ""

    var currencyName: String = ""

    var currencySymbol: String = ""

    var euroEquivalence: BigDecimal = BigDecimal(0)
}