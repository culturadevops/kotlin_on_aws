package com.example.iptrack.exceptions

class UserException(
    var code: String,
    override val message: String? = null,
    var messageArguments: Array<Any>? = null,
) : RuntimeException()