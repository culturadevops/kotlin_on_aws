package com.example.iptrack.requesters

import com.example.iptrack.dtos.Api2CountryResponseDto
import com.example.iptrack.routes.IP2COUNTRY_ROUTE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class IpCountryRequester {

    @Autowired
    private lateinit var requester: Requester

    fun getCountryDataFromIp(ip: String): Api2CountryResponseDto {

        val response = requester.execute(
            "$IP2COUNTRY_ROUTE?$ip",
            HttpMethod.GET,
            null,
            Api2CountryResponseDto::class.java,
            null
        )

        if (response.statusCode.is5xxServerError) {
            throw Exception()
        }

        return response.body ?: Api2CountryResponseDto()
    }
}