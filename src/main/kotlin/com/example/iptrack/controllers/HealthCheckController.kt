package com.example.iptrack.controllers

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/health"])
class HealthCheckController {
    @GetMapping
    fun health() : ResponseEntity<HashMap<String, String>> {
        val map = hashMapOf(Pair("status", "up"))

        return ok(map)
    }
}