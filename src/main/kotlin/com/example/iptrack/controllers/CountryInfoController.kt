package com.example.iptrack.controllers

import com.example.iptrack.dtos.CountryInfoResponseDto
import com.example.iptrack.services.IpDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/ip"])
class CountryInfoController {

    @Autowired
    private lateinit var ipDataService: IpDataService

    @GetMapping("/{ip}")
    fun getDataFromIp(@PathVariable ip: String): CountryInfoResponseDto {

        return ipDataService.getDataFromIp(ip)
    }
}