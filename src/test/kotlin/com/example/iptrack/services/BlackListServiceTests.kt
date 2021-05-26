package com.example.iptrack.services

import com.example.iptrack.CommonTests
import com.example.iptrack.dtos.BlackListRequestDto
import com.example.iptrack.entities.Blacklist
import org.junit.jupiter.api.Test

class BlackListServiceTests : CommonTests() {

    @Test
    fun `save and consult blacklist`() {
        val ips = arrayOf("192.168.1.1", "192.168.1.2")
        val blackListRequestDto = BlackListRequestDto().apply { this.ips = ips }

        blackListService.updateList(blackListRequestDto)

        val blackLists = blackListService.getBlackList()

        blackLists.forEach { assert(ips.contains(it.ip)) }

        blackListService.deleteFromList(blackListRequestDto)
    }

    @Test
    fun `delete from blacklist`() {
        val ips = arrayOf("192.168.1.1", "192.168.1.2")
        val ipsTwo = arrayOf("192.168.1.1")

        val blackListRequestDto = BlackListRequestDto().apply { this.ips = ips }
        val blackListRequestDtoTwo = BlackListRequestDto().apply { this.ips = ipsTwo }

        blackListService.updateList(blackListRequestDto)

        blackListService.deleteFromList(blackListRequestDtoTwo)

        val blackLists = blackListService.getBlackList()

        assert(!blackLists.contains(Blacklist().apply { ipsTwo.first() }))

        blackListService.deleteFromList(blackListRequestDto)
    }
}