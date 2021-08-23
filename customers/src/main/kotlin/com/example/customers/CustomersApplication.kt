package com.example.customers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class CustomersApplication

fun main(args: Array<String>) {
    runApplication<CustomersApplication>(*args)
}

@RestController
class CustomerRestController(private val cr: CustomerRepository) {

    @GetMapping("/customers")
    fun get() = this.cr.findAll()
}


interface CustomerRepository : ReactiveCrudRepository<Customer, Int>

data class Customer(@Id val id: Int, val name: String)