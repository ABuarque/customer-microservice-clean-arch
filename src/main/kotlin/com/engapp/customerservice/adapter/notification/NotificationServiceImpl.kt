package com.engapp.customerservice.adapter.notification

import com.engapp.customerservice.usecase.notification.NotificationService

class NotificationServiceImpl : NotificationService {

    override fun sendTextEmail(to: String, subject: String, body: String) {
        println(">> token: $body")
    }

    override fun sendHTMLEmail(to: String, subject: String, body: String) {
        println(">> token: $body")
    }
}
