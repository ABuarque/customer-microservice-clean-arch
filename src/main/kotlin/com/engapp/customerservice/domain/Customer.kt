package com.engapp.customerservice.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*

data class Customer(val id: String = UUID.randomUUID().toString(),
                    var name: String = "",
                    var email: String = "",

                    @JsonIgnore
                    var password: String = "",

                    var passwordRedefinitionCode: String? = null)
