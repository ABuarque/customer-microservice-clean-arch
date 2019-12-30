package com.engapp.customerservice.adapter.controller

import com.engapp.customerservice.adapter.auth.AuthService
import com.engapp.customerservice.adapter.controller.model.*
import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.ChangePassword
import com.engapp.customerservice.usecase.CreateAccount
import com.engapp.customerservice.usecase.LoginWithEmail
import com.engapp.customerservice.usecase.exception.DefaultException
import com.engapp.customerservice.usecase.exception.ExceptionData

class CustomerController(private val createAccount: CreateAccount,
                         private val loginWithEmail: LoginWithEmail,
                         private val changePassword: ChangePassword,
                         private val authService: AuthService) {

    fun create(customerWeb: CustomerWeb): DefaultWebResponse {
        return try {
            createAccount.create(customerWeb.toDomainCustomer()).toResponseModel()
        } catch (e: DefaultException) {
            e.data.toDefaultWebError()
        } catch (e: Exception) {
            e.toResponseModel()
        }
    }

    fun login(customerWeb: CustomerWeb): DefaultWebResponse {
        return try {
            loginWithEmail.login(customerWeb.email, customerWeb.password).toResponseModel()
        } catch (e: DefaultException) {
            e.data.toDefaultWebError()
        } catch (e: Exception) {
            e.toResponseModel()
        }
    }

    fun changePassword(authToken: String, changePasswordPayload: ChangePasswordPayload): DefaultWebResponse {
        if (!authService.isTokenValid(authToken))
            return DefaultWebResponse(error = DefaultError("Problem in auth", 3, 200, "Invalid credentials"))

        val claimsPackage = authService.toMap(authToken)

        if (claimsPackage.isEmpty)
            return DefaultWebResponse(error = DefaultError("Problem in auth", 4, 200, "Invalid auth data"))

        val id = claimsPackage.get()["id"] as String
        val email = claimsPackage.get()["email"] as String
        val customer = Customer(id = id, email = email)

        return try {
            changePassword.change(customer, changePasswordPayload.currentPassword,
                                            changePasswordPayload.newPassword).toResponseModel()
        } catch (e: DefaultException) {
            e.data.toDefaultWebError()
        } catch (e: Exception) {
            e.toResponseModel()
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

    private fun Exception.toResponseModel(): DefaultWebResponse {
        return DefaultWebResponse(error = DefaultError("Internal error", -1, 500, localizedMessage))
    }
}

