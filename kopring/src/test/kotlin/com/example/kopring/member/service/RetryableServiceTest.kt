package com.example.kopring.member.service

import com.example.kopring.RetryCounter
import com.example.kopring.RetryService
import com.example.kopring.test.IntegrationTest
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@IntegrationTest
class RetryableServiceTest(
    private val retryService: RetryService,
) {
    @BeforeEach
    internal fun setUp() {
        RetryCounter.counter = 0
    }

    @DisplayName("3회 초과하도록 실패하면 예외가 발생한다.")
    @Test
    fun retryTest1() {
        shouldThrowExactly<IllegalArgumentException> {
            retryService.retryFun(pivotCount = 4)
        }
    }

    @DisplayName("3회 까지는 재시도를 한뒤 success 를 응답한다.")
    @Test
    fun retryTest2() {
        shouldNotThrowAny {
            retryService.retryFun(pivotCount = 3) shouldBe "success"
        }
    }
}
