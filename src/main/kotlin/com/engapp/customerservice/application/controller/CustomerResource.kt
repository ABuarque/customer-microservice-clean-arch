package com.engapp.customerservice.application.controller

import com.engapp.customerservice.adapter.controller.CustomerController
import com.engapp.customerservice.adapter.controller.model.CustomerWeb
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/customers")
class CustomerResource(@Autowired private val customerController: CustomerController) {

    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestBody customer: CustomerWeb): ResponseEntity<Any> {
        val created = customerController.create(customer)
        return ResponseEntity(created, HttpStatus.CREATED)
    }

    @RequestMapping("/login", method = [RequestMethod.POST])
    fun loginWithEmail(@RequestBody customer: CustomerWeb): ResponseEntity<Any> {
        val found = customerController.login(customer)
        return ResponseEntity(found, HttpStatus.OK)
    }
}
