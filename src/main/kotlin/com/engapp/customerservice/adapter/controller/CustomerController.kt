package com.engapp.customerservice.adapter.controller

import com.engapp.customerservice.adapter.auth.AuthService
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
                         private val loginWithEmail: LoginWithEmail,
                         private val authService: AuthService) {

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

    private fun ExceptionData.toDefaultWebError(): DefaultWebResponse {
        return DefaultWebResponse(error = DefaultError(title, code, statusCode, description))
    }

    private fun CustomerWeb.toDomainCustomer(): Customer {
        return Customer(name = name, password = password, email = email)
    }

    private fun Customer.toResponseModel(): DefaultWebResponse {
        val token = authService.getToken(this)
        return DefaultWebResponse(data = CustomerResponse(id, email, name, token))
    }
}

