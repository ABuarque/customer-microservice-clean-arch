package com.engapp.customerservice.application.controller

import com.engapp.customerservice.adapter.controller.CustomerController
import com.engapp.customerservice.adapter.controller.model.ChangePasswordPayload
import com.engapp.customerservice.adapter.controller.model.CustomerWeb
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/customers")
class CustomerResource(@Autowired private val customerController: CustomerController) {

    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestBody customer: CustomerWeb): ResponseEntity<Any> {
        return ResponseEntity(customerController.create(customer), HttpStatus.OK)
    }

    @RequestMapping("/login", method = [RequestMethod.POST])
    fun loginWithEmail(@RequestBody customer: CustomerWeb): ResponseEntity<Any> {
        return ResponseEntity(customerController.login(customer), HttpStatus.OK)
    }

    @RequestMapping("/change_password", method = [RequestMethod.POST])
    fun changePassword(@RequestHeader("token") token: String,
                       @RequestBody changePasswordPayload: ChangePasswordPayload): ResponseEntity<Any> {
        return ResponseEntity(customerController.changePassword(token, changePasswordPayload), HttpStatus.OK)
    }

    @RequestMapping("/forgot_password", method = [RequestMethod.POST])
    fun requestPasswordRedefinitionLink(@RequestBody customer: CustomerWeb): ResponseEntity<Any> {
        return ResponseEntity(customerController.requestPasswordRedefinitionLink(customer), HttpStatus.OK)
    }

    @RequestMapping("/reset_password")
    fun redefineForgottenPassword(@RequestBody customer: CustomerWeb): ResponseEntity<Any> {
        return ResponseEntity(customerController.definePasswordByRedefinitionToken(customer), HttpStatus.OK)
    }
}
