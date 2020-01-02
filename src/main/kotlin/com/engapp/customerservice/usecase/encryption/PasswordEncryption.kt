package com.engapp.customerservice.usecase.encryption

/**
 * PasswordEncryption is a blueprint for the
 * situations that this system works with
 * passwords.
 *
 */
interface PasswordEncryption {

    /**
     * encrypt must get a string to be 'hashed' and
     * returned.
     *
     * @param password to be encrypted
     * @return the encrypted password
     */
    fun encrypt(password: String): String

    /**
     * isValid get the string given to be checked
     * and compare it with the stored on database.
     *
     * @param givenToValidate string given to be compared with the stored in database
     * @param gottenFromDatabase string gotten from database
     * @return true if they match, false if not
     */
    fun isValid(givenToValidate: String, gottenFromDatabase: String): Boolean
}
