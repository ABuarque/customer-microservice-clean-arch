package com.engapp.customerservice.adapter.controller.model

data class ChangePasswordPayload(val currentPassword: String = "",
                                 val newPassword: String = "")
