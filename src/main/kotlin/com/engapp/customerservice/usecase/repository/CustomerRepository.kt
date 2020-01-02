package com.engapp.customerservice.usecase.repository

import com.engapp.customerservice.domain.Customer
import java.util.*

/**
 * CustomerRepository is a blueprint to the
 * possible queries that might be used for
 * user domain.
 *
 */
interface CustomerRepository {

    fun findById(id: String): Optional<Customer>

    fun create(customer: Customer): Customer

    fun findByEmail(email: String): Optional<Customer>

    fun update(customer: Customer): Customer

    fun findByPasswordRedefinitionToken(passwordRedefinitionToken: String): Optional<Customer>
}
