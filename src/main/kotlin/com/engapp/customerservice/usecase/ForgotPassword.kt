package com.engapp.customerservice.usecase

import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.exception.UserNotFound
import com.engapp.customerservice.usecase.notification.NotificationService
import com.engapp.customerservice.usecase.repository.CustomerRepository
import org.apache.commons.codec.binary.Base64;
import java.util.*

class ForgotPassword(private val customerRepository: CustomerRepository,
                     private val notificationService: NotificationService) {

    @Throws(UserNotFound::class)
    fun requestPasswordRedefinitionLink(email: String) {
        val customerPackage = customerRepository.findByEmail(email)

        if (customerPackage.isEmpty)
            throw UserNotFound("Was not found any user with email $email")

        val customer = customerPackage.get()

        customer.passwordRedefinitionCode = UUID.randomUUID().toString()

        customerRepository.update(customer)

        sendEmailToCustomerWithRedefinitionLink(customer)
    }

    private fun sendEmailToCustomerWithRedefinitionLink(customer: Customer) {
        val redefinitionTokenAsByteArray = customer.passwordRedefinitionCode!!.toByteArray()

        val encryptedCode = Base64.encodeBase64String(redefinitionTokenAsByteArray)

        notificationService.sendTextEmail(customer.email, "Password redefinition", encryptedCode)
    }

    fun redefineForgottenPassword(passwordRedefinitionToken: String, newPassword: String) {

    }

}
