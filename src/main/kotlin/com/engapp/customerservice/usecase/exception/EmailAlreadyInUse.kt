package com.engapp.customerservice.usecase.exception

class EmailAlreadyInUse(msg: String) : DefaultException(msg) {

    init {
        data.statusCode = 200
        data.code = 0
        data.title = "Email already in use"
    }
}
