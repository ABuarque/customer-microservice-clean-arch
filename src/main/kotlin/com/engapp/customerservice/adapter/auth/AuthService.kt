package com.engapp.customerservice.adapter.auth

import com.engapp.customerservice.domain.Customer
import java.util.*

/**
 * AuthService is a blueprint that exposes
 * possible capabilities to use on this system.
 *
 */
interface AuthService {

    /**
     * getToken gets an instance and returns a token
     * as string.
     *
     * @param customer an instance of Customer
     * @return string the created token
     */
    fun getToken(customer: Customer): String

    fun isTokenValid(token: String): Boolean

    fun toMap(token: String): Optional<Map<String, Any>>
}
