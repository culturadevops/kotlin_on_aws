package com.example.iptrack.dtos

import java.io.Serializable

class RestCountriesResponseDto : Serializable {

    var name: String = ""

    var currencies: List<CurrencyDto> = listOf()
}