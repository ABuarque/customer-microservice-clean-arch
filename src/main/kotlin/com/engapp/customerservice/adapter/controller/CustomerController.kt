package com.engapp.customerservice.adapter.controller

import com.engapp.customerservice.adapter.controller.model.CustomerWeb
import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.CreateAccount
import com.engapp.customerservice.usecase.LoginWithEmail

class CustomerController(private val createAccount: CreateAccount,
                         private val loginWithEmail: LoginWithEmail) {

    fun create(customerWeb: CustomerWeb): Customer {
        return createAccount.create(customerWeb.toDomainCustomer())
    }

    fun login(customerWeb: CustomerWeb): Customer {
        return loginWithEmail.login(customerWeb.email, customerWeb.password)
    }
}

fun CustomerWeb.toDomainCustomer(): Customer {
    return Customer(name = name, password = password, email = email)
}
