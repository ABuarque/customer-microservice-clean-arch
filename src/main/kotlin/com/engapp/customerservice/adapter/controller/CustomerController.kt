package com.engapp.customerservice.adapter.controller

import com.engapp.customerservice.adapter.controller.model.CustomerResponse
import com.engapp.customerservice.adapter.controller.model.CustomerWeb
import com.engapp.customerservice.adapter.controller.model.DefaultError
import com.engapp.customerservice.adapter.controller.model.DefaultWebResponse
import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.CreateAccount
import com.engapp.customerservice.usecase.LoginWithEmail
import com.engapp.customerservice.usecase.exception.DefaultException
import com.engapp.customerservice.usecase.exception.ExceptionData

class CustomerController(private val createAccount: CreateAccount,
                         private val loginWithEmail: LoginWithEmail) {

    fun create(customerWeb: CustomerWeb): DefaultWebResponse {
        return try {
            createAccount.create(customerWeb.toDomainCustomer()).toResponseModel()
        } catch (e: DefaultException) {
            e.data.toDefaultWebError()
        } catch (e: Exception) {
            DefaultWebResponse(error = DefaultError("Internal error", -1, 500, e.localizedMessage))
        }
    }

    fun login(customerWeb: CustomerWeb): DefaultWebResponse {
        return try {
            loginWithEmail.login(customerWeb.email, customerWeb.password).toResponseModel()
        } catch (e: DefaultException) {
            e.data.toDefaultWebError()
        } catch (e: Exception) {
            DefaultWebResponse(error = DefaultError("Internal error", -1, 500, e.localizedMessage))
        }
    }
}

fun ExceptionData.toDefaultWebError(): DefaultWebResponse {
    return DefaultWebResponse(error = DefaultError(title, code, statusCode, description))
}

fun CustomerWeb.toDomainCustomer(): Customer {
    return Customer(name = name, password = password, email = email)
}

fun Customer.toResponseModel(): DefaultWebResponse {
    return DefaultWebResponse(data = CustomerResponse(id, email, name, password))
}
