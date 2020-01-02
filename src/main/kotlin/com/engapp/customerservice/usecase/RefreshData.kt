package com.engapp.customerservice.usecase

import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.exception.UserNotFound
import com.engapp.customerservice.usecase.repository.CustomerRepository

class RefreshData(private val customerRepository: CustomerRepository) {

    @Throws(UserNotFound::class)
    fun refresh(id: String): Customer {
        val customerPackage = customerRepository.findById(id)

        if (customerPackage.isEmpty)
            throw UserNotFound("User not found")

        return customerPackage.get()
    }
}
