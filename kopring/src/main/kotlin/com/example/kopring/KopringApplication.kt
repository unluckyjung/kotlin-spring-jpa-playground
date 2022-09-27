package com.example.kopring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class KopringApplication

fun main(args: Array<String>) {
    runApplication<KopringApplication>(*args)
}