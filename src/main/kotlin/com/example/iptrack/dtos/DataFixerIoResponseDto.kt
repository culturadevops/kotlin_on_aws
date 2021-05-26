package com.example.iptrack.dtos

import java.io.Serializable

class DataFixerIoResponseDto: Serializable {

    var success: String = ""

    var base: String = ""

    var date: String = ""

    var rates: HashMap<String, Double?> = hashMapOf()
}