package com.engapp.customerservice.usecase.notification

interface NotificationService {

    fun sendTextEmail(to: String, subject: String, body: String)

    fun sendHTMLEmail(to: String, subject: String, body: String)
}
