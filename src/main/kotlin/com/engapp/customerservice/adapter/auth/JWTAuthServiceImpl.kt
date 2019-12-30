package com.engapp.customerservice.adapter.auth

import com.engapp.customerservice.domain.Customer
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import java.util.*

class JWTAuthServiceImpl  : AuthService {

    private companion object {
        val INTERNAL_TOKEN = System.getenv("CUSTOMER_AUTH_TOKEN") ?: "9S8BKABS5VdddTSnsd0dm87dsnBB7%VH%gs740MJnb509J"
        val INTERNAL_TOKEN_BYTES = INTERNAL_TOKEN.toByteArray()
        const val VALID_DAYS = 1
    }

    override fun getToken(customer: Customer): String {
        val signer = MACSigner(INTERNAL_TOKEN_BYTES)
        val claimsSet: JWTClaimsSet
        try {
            claimsSet = JWTClaimsSet.Builder()
                    .expirationTime(Date(System.currentTimeMillis() + 86400000 * VALID_DAYS))
                    .claim("id", customer.id)
                    .claim("email", customer.email)
                    .build()
        } catch (e: Exception) {
            throw Exception()
        }
        val signedJWT = SignedJWT(JWSHeader(JWSAlgorithm.HS256), claimsSet)
        signedJWT.sign(signer)
        return signedJWT.serialize()
    }

    override fun isTokenValid(token: String): Boolean {
        return try {
            val signedJWT = SignedJWT.parse(token)
            signedJWT.verify(MACVerifier(INTERNAL_TOKEN))
        } catch (e: Exception) {
            false
        }
    }

    override fun toMap(token: String): Map<String, Any> {
        try {
            val signedJWT = SignedJWT.parse(token)
            return signedJWT!!.jwtClaimsSet.claims.toMap()
        } catch (e: Exception) {
            throw Exception("Failed to get claims")
        }
    }
}
