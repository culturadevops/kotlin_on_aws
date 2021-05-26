package com.example.iptrack.services

import com.example.iptrack.dtos.BlackListRequestDto
import com.example.iptrack.entities.Blacklist
import com.example.iptrack.repositories.BlackListRepository
import com.example.iptrack.repositories.BlackListRepositorys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BlackListService {

    @Autowired
    private lateinit var blackListRepository: BlackListRepository

    fun getBlackList(): MutableList<Blacklist> {
        return blackListRepository.findAll()
    }

    fun updateList(blackListRequestDto: BlackListRequestDto): List<Blacklist?> {

        blackListRequestDto.ips.forEach {
            blackListRepository.save(Blacklist().apply { ip = it })
        }

        return blackListRepository.findAll()
    }

    fun deleteFromList(blackListRequestDto: BlackListRequestDto): List<Blacklist> {

        blackListRequestDto.ips.forEach {
            blackListRepository.delete(Blacklist().apply { ip = it })
        }

        return blackListRepository.findAll()
    }

    fun existElement(ip: String): Boolean {

        return  blackListRepository.existsById(ip)
    }
}