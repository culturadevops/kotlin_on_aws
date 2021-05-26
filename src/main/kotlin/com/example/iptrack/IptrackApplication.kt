package com.example.iptrack

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IptrackApplication

fun main(args: Array<String>) {
	runApplication<IptrackApplication>(*args)
}
