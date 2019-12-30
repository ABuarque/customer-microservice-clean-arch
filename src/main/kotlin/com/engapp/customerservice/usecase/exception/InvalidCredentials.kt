package com.engapp.customerservice.usecase.exception

class InvalidCredentials(msg: String) : DefaultException(msg) {

    init {
        data.statusCode = 200
        data.code = 1
        data.title = "Invalid Credentials"
    }
}
