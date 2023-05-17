package com.example.kopring

import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service


object RetryCounter {
    var counter = 0
}

@Service
class RetryService {
    @Retryable(
        value = [IllegalArgumentException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1000)
    )
    fun retryFun(pivotCount: Int): String {
        RetryCounter.counter++
        println("try ${RetryCounter.counter}")

        if (RetryCounter.counter < pivotCount) {
            throw IllegalArgumentException()
        }
        return "success"
    }
}
