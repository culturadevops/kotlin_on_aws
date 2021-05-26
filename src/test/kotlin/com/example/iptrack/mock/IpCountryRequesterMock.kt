package com.example.iptrack.mock

import com.example.iptrack.dtos.Api2CountryResponseDto
import com.example.iptrack.requesters.IpCountryRequester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.stereotype.Component
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.doReturn

@Component
class IpCountryRequesterMock: CommonMock() {

    @MockBean
    private lateinit var ipCountryRequester: IpCountryRequester

    fun getCountryDataFromIp(response: Api2CountryResponseDto) {
        doReturn(response).`when`(ipCountryRequester).getCountryDataFromIp(anyString())
    }
}