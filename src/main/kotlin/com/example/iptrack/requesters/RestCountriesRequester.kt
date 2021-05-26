package com.example.iptrack.requesters

import com.example.iptrack.contants.CacheConstants.REST_COUNTRIES
import com.example.iptrack.dtos.RestCountriesResponseDto
import com.example.iptrack.routes.RESTCOUINTRIES_ROUTE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class RestCountriesRequester {

    @Autowired
    private lateinit var requester: Requester

    @Cacheable(
        value  = [REST_COUNTRIES],
        cacheManager = "cacheManager1Hour",
        key = "'$REST_COUNTRIES' + #countryName"
    )
    fun getDataFromCountryCode(countryName: String): RestCountriesResponseDto {

        val response = requester.execute(
            "$RESTCOUINTRIES_ROUTE/$countryName",
            HttpMethod.GET,
            null,
            RestCountriesResponseDto::class.java,
            null
        )

        if (response.statusCode.is5xxServerError) {
            throw Exception()
        }

        return response.body ?: RestCountriesResponseDto()
    }
}