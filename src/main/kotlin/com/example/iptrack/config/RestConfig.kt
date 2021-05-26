package com.example.iptrack.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfig {

    private val timeoutRequest = 2000

    private val requestReadTimeout = 2000

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate(getClientHttpRequestFactory())
    }

    private fun getClientHttpRequestFactory(): ClientHttpRequestFactory {
        val clientHttpRequestFactory = HttpComponentsClientHttpRequestFactory()

        clientHttpRequestFactory.setConnectTimeout(timeoutRequest)
        clientHttpRequestFactory.setReadTimeout(requestReadTimeout)

        return clientHttpRequestFactory;
    }

    @Bean
    fun byteArrayHttpMessageConverter(): ByteArrayHttpMessageConverter {
        return ByteArrayHttpMessageConverter()
    }
}