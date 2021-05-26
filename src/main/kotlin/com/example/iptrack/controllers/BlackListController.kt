package com.example.iptrack.controllers

import com.example.iptrack.dtos.BlackListRequestDto
import com.example.iptrack.dtos.CountryInfoResponseDto
import com.example.iptrack.entities.Blacklist
import com.example.iptrack.services.BlackListService
import com.example.iptrack.services.IpDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/blacklist"])
class BlackListController {

    @Autowired
    private lateinit var blackListService: BlackListService

    @PostMapping
    fun addToBlackList(@RequestBody blackListRequestDto: BlackListRequestDto): List<Blacklist?> {

        return blackListService.updateList(blackListRequestDto)
    }

    @DeleteMapping
    fun deleteBlackList(@RequestBody blackListRequestDto: BlackListRequestDto): List<Blacklist> {

        return blackListService.deleteFromList(blackListRequestDto)
    }
}