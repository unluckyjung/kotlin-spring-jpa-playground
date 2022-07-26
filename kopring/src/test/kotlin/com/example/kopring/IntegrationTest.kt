package com.example.kopring

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class IntegrationTest {
    @Test
    fun test1() {
        println("test1 start: ${Thread.currentThread().name}")
        Thread.sleep(500)
        println("test1 end: ${Thread.currentThread().name}")
    }

    @Test
    fun test2() {
        println("test2 start: ${Thread.currentThread().name}")
        Thread.sleep(500)
        println("test2 end: ${Thread.currentThread().name}")
    }
}
