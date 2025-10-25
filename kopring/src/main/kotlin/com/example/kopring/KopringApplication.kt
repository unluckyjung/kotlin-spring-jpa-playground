package com.example.kopring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
class KopringApplication

fun main(args: Array<String>) {
    val app = org.springframework.boot.SpringApplication(KopringApplication::class.java)
    app.setAdditionalProfiles("mysql")   // mysql 프로필 설정
    runApplication<KopringApplication>(*args)
}
