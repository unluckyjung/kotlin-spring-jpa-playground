package com.example.kopring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
@EnableCaching
class KopringApplication

fun main(args: Array<String>) {
    runApplication<KopringApplication>(*args)
}