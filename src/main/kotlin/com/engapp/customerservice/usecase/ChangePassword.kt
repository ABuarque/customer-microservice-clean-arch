package com.engapp.customerservice.usecase

import com.engapp.customerservice.adapter.passwordencryption.PasswordEncryptionService
import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.exception.InvalidCredentials
import com.engapp.customerservice.usecase.exception.UserNotFound
import com.engapp.customerservice.usecase.repository.CustomerRepository

class ChangePassword(private val customerRepository: CustomerRepository,
                     private val encryptionService: PasswordEncryptionService) {

    @Throws(UserNotFound::class, InvalidCredentials::class)
    fun change(customer: Customer, currentPassword: String, newPassword: String): Customer {
        val foundCustomer = customerRepository.findByEmail(customer.email)

        if (foundCustomer.isEmpty)
            throw UserNotFound("No customers with email ${customer.email} found")

        if (!encryptionService.isValid(currentPassword, foundCustomer.get().password))
            throw InvalidCredentials("Invalid credentials, cannot change password")

        val encryptedPassword = encryptionService.encrypt(newPassword)

        foundCustomer.get().password = encryptedPassword

        return customerRepository.update(foundCustomer.get())
    }
}
