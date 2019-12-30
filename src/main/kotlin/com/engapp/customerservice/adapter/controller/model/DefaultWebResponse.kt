package com.engapp.customerservice.adapter.controller.model

data class DefaultError(var title: String = "",
                        var code: Long = 0,
                        var statusCode: Long = 0,
                        var description: String = "")

data class DefaultWebResponse(val data: Any? = null,
                              val error: DefaultError? = null)
