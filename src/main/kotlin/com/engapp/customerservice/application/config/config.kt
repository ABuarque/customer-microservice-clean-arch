package com.engapp.customerservice.application.config

import com.engapp.customerservice.adapter.auth.JWTAuthServiceImpl
import com.engapp.customerservice.adapter.controller.CustomerController
import com.engapp.customerservice.config.FakeConfigComponent
import com.engapp.customerservice.usecase.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    private val configuration = FakeConfigComponent()

    @Bean
    fun createAccount(): CreateAccount {
        return configuration.create()
    }

    @Bean
    fun loginWithEmail(): LoginWithEmail {
        return configuration.login()
    }

    @Bean
    fun changePassword(): ChangePassword {
        return configuration.changePassword()
    }

    @Bean
    fun requestForgottenPasswordRedefinition(): RequestForgottenPasswordRedefinition {
        return configuration.requestForgottenPasswordRedefinition()
    }

    @Bean
    fun redefineForgottenPassword(): RedefineForgottenPassword {
        return configuration.redefineForgottenPassword()
    }

    @Bean
    fun refreshData(): RefreshData {
        return configuration.refreshData()
    }

    @Bean
    fun customerController(): CustomerController {
        return CustomerController(createAccount(),
                                loginWithEmail(),
                                changePassword(),
                                requestForgottenPasswordRedefinition(),
                                redefineForgottenPassword(),
                                refreshData(),
                                JWTAuthServiceImpl())
    }
}
