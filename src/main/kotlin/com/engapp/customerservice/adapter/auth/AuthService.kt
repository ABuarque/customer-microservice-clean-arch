package com.engapp.customerservice.adapter.auth

import com.engapp.customerservice.domain.Customer
import java.util.*

interface AuthService {

    fun getToken(customer: Customer): String

    fun isTokenValid(token: String): Boolean

    fun toMap(token: String): Optional<Map<String, Any>>
}
