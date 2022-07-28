package com.example.kopring

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
// @ResourceLock(value = "resources") // 해당 옵션을 주면 클래스내 테스트끼리는 하나의 스레드에서 작동
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
