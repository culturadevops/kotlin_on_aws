package com.example.iptrack.requesters

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import kotlin.jvm.Throws

@Component
class Requester {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private fun getBasicHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return headers
    }


    fun getHeaders(): HttpHeaders {
        val defaultHttpHeaders = HttpHeaders()

        defaultHttpHeaders[HttpHeaders.CONTENT_TYPE] = MediaType.APPLICATION_JSON_VALUE

        return defaultHttpHeaders
    }

    fun <T> execute(
        url: String,
        method: HttpMethod,
        requestEntity: Any? = null,
        responseEntity: Class<T>,
        headers: HashMap<String, String>? = null
    ): ResponseEntity<T> {

        val request = HttpEntity(requestEntity, this.getHeaders())
        val response: ResponseEntity<T>

        response = try {

            restTemplate.exchange(url, method, request, responseEntity)
        } catch (exception: Exception) {

            logger.error(
                "Url {},  Error message {},  with body {} and with method {}",
                url,
                exception.message,
                objectMapper.writeValueAsString(request.body),
                method.toString()
            )

            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

        if (response.statusCode.is5xxServerError || response.statusCode.is4xxClientError) {
            logger.error(
                "Url {},  Error message {},  with body {} and with method {}",
                url,
                response.statusCode,
                objectMapper.writeValueAsString(request.body),
                method.toString()
            )
        }

        return response
    }
}