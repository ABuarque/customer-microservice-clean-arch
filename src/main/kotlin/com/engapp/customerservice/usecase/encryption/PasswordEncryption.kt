package com.engapp.customerservice.usecase.encryption

interface PasswordEncryption {

    fun encrypt(password: String): String

    fun isValid(givenToValidate: String, gottenFromDatabase: String): Boolean
}
