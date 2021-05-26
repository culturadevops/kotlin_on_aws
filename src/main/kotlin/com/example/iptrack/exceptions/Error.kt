package com.example.iptrack.exceptions

data class Error(
    var path: String?,
    var code: String,
    var status: String?,
    var message: String?
)
