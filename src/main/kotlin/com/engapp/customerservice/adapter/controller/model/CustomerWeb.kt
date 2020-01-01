package com.engapp.customerservice.adapter.controller.model

data class CustomerWeb(val name: String = "",
                       val email: String = "",
                       val password: String = "",
                       val newPassword: String = "",
                       val passwordRedefinitionToken: String = "")

