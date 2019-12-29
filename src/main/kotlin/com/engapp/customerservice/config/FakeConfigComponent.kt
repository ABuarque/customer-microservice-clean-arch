package com.engapp.customerservice.config

import com.engapp.customerservice.adapter.passwordencryption.PasswordEncryptionService
import com.engapp.customerservice.adapter.repository.FakeCustomerRepository
import com.engapp.customerservice.usecase.CreateAccount
import com.engapp.customerservice.usecase.LoginWithEmail

class FakeConfigComponent {

    private val fakeCustomerRepository = FakeCustomerRepository()
    private val passwordEncryption = PasswordEncryptionService()

    fun create(): CreateAccount {
        return CreateAccount(fakeCustomerRepository, passwordEncryption)
    }

    fun login(): LoginWithEmail {
        return LoginWithEmail(fakeCustomerRepository, passwordEncryption)
    }
}
