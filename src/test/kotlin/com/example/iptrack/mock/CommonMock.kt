package com.example.iptrack.mock

import org.mockito.Mockito

abstract class CommonMock {

    protected fun <T> anyObject(): T {
        return Mockito.any()
    }
}