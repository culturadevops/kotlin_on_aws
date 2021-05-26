package com.example.iptrack.handlers

import com.example.iptrack.exceptions.Error
import com.example.iptrack.exceptions.ErrorContainer
import com.example.iptrack.exceptions.UserException
import javax.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandler {

    private var logger = LoggerFactory.getLogger(this.javaClass)

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleGenericError(request: HttpServletRequest, ex: Exception): ErrorContainer {

        logger.error(ex.message, ex)

        return ErrorContainer(
            Error(
                request.requestURI,
                "generic_error",
                HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
                "generic_error"
            )
        )
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException::class)
    fun handleCustomError(request: HttpServletRequest, exception: UserException): ErrorContainer {

        val errorMessage = exception.message

        logger.warn(errorMessage)

        return ErrorContainer(
            Error(
                request.requestURI,
                exception.code,
                HttpStatus.BAD_REQUEST.value().toString(),
                errorMessage
            )
        )
    }
}
