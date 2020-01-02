package com.engapp.customerservice.config

import com.engapp.customerservice.adapter.notification.NotificationServiceImpl
import com.engapp.customerservice.adapter.passwordencryption.PasswordEncryptionService
import com.engapp.customerservice.adapter.repository.FakeCustomerRepository
import com.engapp.customerservice.usecase.*

class FakeConfigComponent {

    private val fakeCustomerRepository = FakeCustomerRepository()
    private val passwordEncryption = PasswordEncryptionService()
    private val notificationService = NotificationServiceImpl()

    fun create(): CreateAccount {
        return CreateAccount(fakeCustomerRepository, passwordEncryption)
    }

    fun login(): LoginWithEmail {
        return LoginWithEmail(fakeCustomerRepository, passwordEncryption)
    }

    fun changePassword(): ChangePassword {
        return ChangePassword(fakeCustomerRepository, passwordEncryption)
    }

    fun requestForgottenPasswordRedefinition(): RequestForgottenPasswordRedefinition {
        return RequestForgottenPasswordRedefinition(fakeCustomerRepository, notificationService)
    }

    fun redefineForgottenPassword(): RedefineForgottenPassword {
        return RedefineForgottenPassword(fakeCustomerRepository, passwordEncryption)
    }
}
