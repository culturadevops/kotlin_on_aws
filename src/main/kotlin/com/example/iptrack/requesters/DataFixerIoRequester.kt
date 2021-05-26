package com.example.iptrack.requesters

import com.example.iptrack.contants.CacheConstants.DATA_FIXER_IO
import com.example.iptrack.dtos.DataFixerIoResponseDto
import com.example.iptrack.routes.DATA_FIXER_IO_ROUTE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class DataFixerIoRequester {

    @Value(value = "\${DATA_FIXED_IO_ACCESS_KEY}")
    private lateinit var dataFixedIoAccessKey: String

    @Autowired
    private lateinit var requester: Requester

    @Cacheable(
        value  = [DATA_FIXER_IO],
        cacheManager = "cacheManager1Hour",
        key = "'$DATA_FIXER_IO'"
    )
    fun getCurrencies(): DataFixerIoResponseDto {
        val route = "$DATA_FIXER_IO_ROUTE?access_key=$dataFixedIoAccessKey"
        val response = requester.execute(
            route,
            HttpMethod.GET,
            null,
            DataFixerIoResponseDto::class.java,
            null
        )

        if (response.statusCode.is5xxServerError) {
            throw Exception()
        }

        return response.body ?: DataFixerIoResponseDto()
    }
}