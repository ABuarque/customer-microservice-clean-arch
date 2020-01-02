package com.engapp.customerservice.adapter.controller

import com.engapp.customerservice.adapter.auth.AuthService
import com.engapp.customerservice.adapter.controller.model.*
import com.engapp.customerservice.domain.Customer
import com.engapp.customerservice.usecase.*
import com.engapp.customerservice.usecase.exception.DefaultException
import com.engapp.customerservice.usecase.exception.ExceptionData

class CustomerController(private val createAccount: CreateAccount,
                         private val loginWithEmail: LoginWithEmail,
                         private val changePassword: ChangePassword,
                         private val requestForgottenPasswordRedefinition: RequestForgottenPasswordRedefinition,
                         private val redefineForgottenPassword: RedefineForgottenPassword,
                         private val refreshData: RefreshData,
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

        val customer = claimsPackage.get().toCustomer()

        return try {
            changePassword.change(customer, changePasswordPayload.currentPassword,
                                            changePasswordPayload.newPassword).toResponseModel()
        } catch (e: DefaultException) {
            e.data.toDefaultWebError()
        } catch (e: Exception) {
            e.toResponseModel()
        }
    }

    fun requestPasswordRedefinitionLink(customerWeb: CustomerWeb): DefaultWebResponse {
        return try {
            requestForgottenPasswordRedefinition.request(customerWeb.email)
            val responsePayload = HashMap<String, String>()
            responsePayload["message"] = "If there is some user with email ${customerWeb.email} it will receive a message"
            DefaultWebResponse(data = responsePayload)
        } catch (e: DefaultException) {
            e.data.toDefaultWebError()
        } catch (e: Exception) {
            e.toResponseModel()
        }
    }

    fun definePasswordByRedefinitionToken(customerWeb: CustomerWeb): DefaultWebResponse {
        return try {
            redefineForgottenPassword.redefine(customerWeb.passwordRedefinitionToken,
                                                                  customerWeb.newPassword).toResponseModel()
        } catch (e: DefaultException) {
            e.data.toDefaultWebError()
        } catch (e: Exception) {
            e.toResponseModel()
        }
    }

    fun refreshData(authToken: String): DefaultWebResponse {
        if (!authService.isTokenValid(authToken))
            return DefaultWebResponse(error = DefaultError("Problem in auth", 3, 200, "Invalid credentials"))

        val claimsPackage = authService.toMap(authToken)

        if (claimsPackage.isEmpty)
            return DefaultWebResponse(error = DefaultError("Problem in auth", 4, 200, "Invalid auth data"))

        val customer = claimsPackage.get().toCustomer()

        return try {
            refreshData.refresh(customer.id).toResponseModel()
        } catch (e: DefaultException) {
            e.data.toDefaultWebError()
        } catch (e: Exception) {
            e.printStackTrace()
            e.toResponseModel()
        }
    }

    private fun Map<String, Any>.toCustomer(): Customer {
        val id = this["id"] as String
        val email = this["email"] as String
        return Customer(id = id, email = email)
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

