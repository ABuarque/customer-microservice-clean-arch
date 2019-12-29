package com.engapp.customerservice.usecase

import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.repository.CustomerRepository
import com.engapp.customerservice.usecase.encryption.PasswordEncryption
import com.engapp.customerservice.usecase.exception.EmailAlreadyInUse

class CreateAccount(private val customerRepository: CustomerRepository,
                    private val passwordEncryption: PasswordEncryption) {

    @Throws(EmailAlreadyInUse::class)
    fun create(customer: Customer): Customer {
        if (customerRepository.findByEmail(customer.email).isPresent)
            throw EmailAlreadyInUse("Email ${customer.email} already in use")
        val encryptedPassword = passwordEncryption.encrypt(customer.password)
        customer.password = encryptedPassword
        return customerRepository.create(customer)
    }
}
