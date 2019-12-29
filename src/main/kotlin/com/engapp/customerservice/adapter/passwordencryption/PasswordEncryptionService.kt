package com.engapp.customerservice.adapter.passwordencryption

import com.engapp.customerservice.usecase.encryption.PasswordEncryption
import org.mindrot.jbcrypt.BCrypt

class PasswordEncryptionService : PasswordEncryption {

    override fun encrypt(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    override fun isValid(givenToValidate: String, gottenFromDatabase: String): Boolean {
        return BCrypt.checkpw(givenToValidate, gottenFromDatabase)
    }

}
