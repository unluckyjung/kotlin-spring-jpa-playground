package com.example.kopring.common

import com.example.kopring.test.IntegrationTest
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@IntegrationTest
class SpringAsyncTest(
    val dummyService: DummyService
) {
    @Test
    fun asyncTest() {
        dummyService.asyncFun()
        dummyService.nonAsyncFun()
    }

    @Test
    fun nonAsyncTest() {
        // 전부 순차적으로 로깅
        dummyService.nonAsyncFun()
        dummyService.nonAsyncFun()
    }
}


@Service
class DummyService {

    @Async("threadPoolTaskExecutor")
    fun asyncFun() {
        for (num in 1..10) {
            logger.info("AsyncFun: $num")
        }
    }

    fun nonAsyncFun() {
        for (num in 1..10) {
            logger.info("NonAsyncFun: $num")
        }
    }


    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}
