package com.engapp.customerservice.usecase.exception

data class ExceptionData(var title: String = "",
                         var code: Long = 0,
                         var statusCode: Long = 0,
                         var description: String = "")

open class DefaultException(message: String) : Exception(message) {

    var data = ExceptionData(description =  message)
}

