package com.engapp.customerservice.application.config

import com.engapp.customerservice.adapter.controller.CustomerController
import com.engapp.customerservice.config.FakeConfigComponent
import com.engapp.customerservice.usecase.CreateAccount
import com.engapp.customerservice.usecase.LoginWithEmail
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
    fun customerController(): CustomerController {
        return CustomerController(createAccount(), loginWithEmail())
    }
}
