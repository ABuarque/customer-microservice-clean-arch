package com.engapp.customerservice.adapter.auth

import com.engapp.customerservice.domain.Customer
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class JWTAuthServiceImplTest {

    private val authService = JWTAuthServiceImpl()
    private val customer = Customer(name = "Aurelio", email = "email", password = "12345678")

    @Test
    fun get_a_customer_instance_and_create_a_token() {
        val createdToken = authService.getToken(customer)
        assertNotNull(createdToken)
    }

    @Test
    fun get_a_given_token_and_should_get_true_for_valid() {
        val token = authService.getToken(customer)
        assertEquals(authService.isTokenValid(token), true)
    }

    @Test
    fun get_a_given_token_and_should_get_false_for_valid() {
        val randomStringValueToBeTheToken = "234rvcsafgyt543wsdfgyt543wsd"
        assertEquals(authService.isTokenValid(randomStringValueToBeTheToken), false)
    }

    @Test
    fun success_on_create_token_from_customer_and_get_params_from_claims() {
        val createdToken = authService.getToken(customer)
        val claims = authService.toMap(createdToken)
        assertEquals(claims["email"], customer.email)
        assertEquals(claims["id"], customer.id)
    }
}