package com.example.iptrack.mock

import com.example.iptrack.dtos.DataFixerIoResponseDto
import com.example.iptrack.requesters.DataFixerIoRequester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.stereotype.Component
import org.mockito.Mockito.doReturn

@Component
class DataFixerIoRequesterMock: CommonMock() {

    @MockBean
    private lateinit var dataFixerIoRequester: DataFixerIoRequester

    fun getCurrencies(response: DataFixerIoResponseDto) {
        doReturn(response).`when`(dataFixerIoRequester).getCurrencies()
    }
}