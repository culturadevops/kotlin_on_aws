package com.example.iptrack

import com.example.iptrack.mock.DataFixerIoRequesterMock
import com.example.iptrack.mock.IpCountryRequesterMock
import com.example.iptrack.mock.RestCountriesRequesterMock
import com.example.iptrack.services.BlackListService
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
abstract class CommonTests {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var blackListService: BlackListService

    @Autowired
    protected lateinit var dataFixerIoRequesterMock: DataFixerIoRequesterMock

    @Autowired
    protected lateinit var ipCountryRequesterMock: IpCountryRequesterMock

    @Autowired
    protected lateinit var restCountriesRequesterMock: RestCountriesRequesterMock

    protected fun <T> anyObject(): T {
        return Mockito.any()
    }
}