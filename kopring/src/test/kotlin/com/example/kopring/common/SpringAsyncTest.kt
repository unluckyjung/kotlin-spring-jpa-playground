package com.example.kopring.common

import com.example.kopring.KopringApplication
import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.core.task.SyncTaskExecutor
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@IntegrationTest
class SpringAsyncTest(
    private val dummyService: DummyService,

    @Qualifier("customThreadPoolTaskExecutor")
    private val customThreadPoolTaskExecutor: TaskExecutor
) {
    @Test
    fun asyncTest() {
        dummyService.asyncFun()
        val executor = customThreadPoolTaskExecutor as ThreadPoolTaskExecutor
        executor.threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS)

        ObjectNumber.count shouldBe 1
    }
}

@IntegrationTest
class SpringAsyncTest2(
    private val dummyService: DummyService,
) {
    @Import(KopringApplication::class)
    @Configuration
    internal class SyncTaskExecutorConfigForTest {
        @Bean("customThreadPoolTaskExecutor")
        fun customThreadPoolTaskExecutor(): TaskExecutor {
            return SyncTaskExecutor()
        }
    }

    @Test
    fun asyncTest() {
        dummyService.asyncFun()

        ObjectNumber.count shouldBe 1
    }
}

@Service
class DummyService(
) {

    @Async("customThreadPoolTaskExecutor")
    fun asyncFun() {
        ObjectNumber.countUp()
        logger.info("this thread is ${Thread.currentThread().name}")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}

object ObjectNumber {
    var count: Int = 0
        private set

    fun countUp() {
        count++
    }
}