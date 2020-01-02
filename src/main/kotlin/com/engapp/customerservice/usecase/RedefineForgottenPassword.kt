package com.engapp.customerservice.usecase

import com.engapp.customerservice.adapter.passwordencryption.PasswordEncryptionService
import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.exception.UserNotFound
import com.engapp.customerservice.usecase.repository.CustomerRepository
import org.apache.commons.codec.binary.Base64;

class RedefineForgottenPassword(private val customerRepository: CustomerRepository,
                                private val encryptionService: PasswordEncryptionService) {

    @Throws(UserNotFound::class)
    fun redefine(passwordRedefinitionToken: String, newPassword: String): Customer {
        val decodedPasswordRedefinitionCode = String(Base64.decodeBase64(passwordRedefinitionToken))

        val customerPackage = customerRepository
                                .findByPasswordRedefinitionToken(decodedPasswordRedefinitionCode)

        if (customerPackage.isEmpty)
            throw UserNotFound("Redefinition code has expire")

        val customer = customerPackage.get()

        customer.password = encryptionService.encrypt(newPassword)
        customer.passwordRedefinitionCode = null

        customerRepository.update(customer)

        sendEmailToCustomerTellingPasswordHasChanged(customer)

        return customer
    }

    private fun sendEmailToCustomerTellingPasswordHasChanged(customer: Customer) {
        println(">> IMPLEMENTING")
    }
}
