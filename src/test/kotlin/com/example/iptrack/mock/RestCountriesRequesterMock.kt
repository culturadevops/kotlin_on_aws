package com.example.iptrack.mock

import com.example.iptrack.dtos.RestCountriesResponseDto
import com.example.iptrack.requesters.RestCountriesRequester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.stereotype.Component
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.doReturn

@Component
class RestCountriesRequesterMock: CommonMock() {

    @MockBean
    private lateinit var restCountriesRequester: RestCountriesRequester

    fun getCountryDataFromIp(response: RestCountriesResponseDto) {
        doReturn(response).`when`(restCountriesRequester).getDataFromCountryCode(anyString())
    }
}