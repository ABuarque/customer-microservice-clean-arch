package com.engapp.customerservice.usecase.repository

import com.engapp.customerservice.domain.Customer
import java.util.*

interface CustomerRepository {

    fun create(customer: Customer): Customer

    fun findByEmail(email: String): Optional<Customer>

    fun update(customer: Customer): Customer
}
