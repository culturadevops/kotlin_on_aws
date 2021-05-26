package com.example.iptrack.dtos

import java.io.Serializable

class BlackListRequestDto: Serializable {

    var ips: Array<String> = arrayOf()
}