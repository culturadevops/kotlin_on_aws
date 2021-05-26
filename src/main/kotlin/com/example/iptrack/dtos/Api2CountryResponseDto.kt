package com.example.iptrack.dtos

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

class Api2CountryResponseDto {

    var countryCode: String = ""

    var countryCode3: String = ""

    var countryName: String = ""

    var countryEmoji: String = ""
}