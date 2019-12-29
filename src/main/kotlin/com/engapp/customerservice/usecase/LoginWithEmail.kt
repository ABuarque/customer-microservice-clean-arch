package com.engapp.customerservice.usecase

import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.encryption.PasswordEncryption
import com.engapp.customerservice.usecase.exception.InvalidCredentials
import com.engapp.customerservice.usecase.exception.UserNotFound
import com.engapp.customerservice.usecase.repository.CustomerRepository

class LoginWithEmail(private val customerRepository: CustomerRepository,
                     private val passwordEncryption: PasswordEncryption) {

    @Throws(UserNotFound::class, InvalidCredentials::class)
    fun login(email: String, password: String): Customer {
        val customer = customerRepository.findByEmail(email)

        if (customer.isEmpty)
            throw UserNotFound("User not found")

        if (!passwordEncryption.isValid(password, customer.get().password))
            throw InvalidCredentials("Email or password incorrect")

        return customer.get()
    }
}
