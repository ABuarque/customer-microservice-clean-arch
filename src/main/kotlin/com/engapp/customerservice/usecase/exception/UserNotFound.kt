package com.engapp.customerservice.usecase.exception

class UserNotFound(msg: String) : DefaultException(msg) {

    init {
        data.statusCode = 200
        data.code = 2
        data.title = "User not found"
    }
}
