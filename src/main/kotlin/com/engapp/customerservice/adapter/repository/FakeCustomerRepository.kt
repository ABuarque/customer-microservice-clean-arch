package com.engapp.customerservice.adapter.repository

import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.repository.CustomerRepository
import java.util.*
import kotlin.collections.HashMap

class FakeCustomerRepository : CustomerRepository {

    private companion object {
        val db = HashMap<String, Customer>()
    }

    override fun create(customer: Customer): Customer {
        db[customer.id] = customer
        return customer
    }

    override fun findByEmail(email: String): Optional<Customer> {
        db.values.forEach {
            if (it.email == email)
                return Optional.of(it)
        }
        return Optional.empty()
    }

    override fun update(customer: Customer): Customer {
        db[customer.id] = customer
        return customer
    }

    override fun findByPasswordRedefinitionToken(passwordRedefinitionToken: String): Optional<Customer> {
        db.values.forEach {
            if (it.passwordRedefinitionCode == passwordRedefinitionToken)
                return Optional.of(it)
        }
        return Optional.empty()
    }
}
