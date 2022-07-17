package com.example.kopring.common

import com.example.kopring.test.IntegrationTest
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

@IntegrationTest
class SpringAsyncTest(
    val dummyService: DummyService
) {
    @Test
    fun asyncTest() {
        dummyService.asyncFun()
        dummyService.syncFun()
    }

    @Test
    fun nonAsyncTest() {
        // 전부 순차적으로 로깅
        dummyService.syncFun()
        dummyService.syncFun()
    }

    @Test
    fun asyncStringReturnTest() {
        for (i in 1..10) {
            println(dummyService.asyncFunString(i))
        }

        for (i in 1..10) {
            println(dummyService.syncFunString(i))
        }

        for (i in 1..10) {
            val asyncFunStringFuture = dummyService.asyncFunStringFuture(i)
            println(asyncFunStringFuture)

            // 결과를 얻을때까지 블로킹
            println(asyncFunStringFuture.get())
        }

        for (i in 1..10) {
            val asyncFunStringFuture = dummyService.asyncFunStringFuture2(i)

            // asyncFunStringFuture.isDone // isDone 을 이용해 완료된 뒤에 호출할 수 도 있음.
            println(asyncFunStringFuture.get())
        }
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

    fun syncFun() {
        for (num in 1..10) {
            logger.info("NonAsyncFun: $num")
        }
    }

    @Async("threadPoolTaskExecutor")
    fun asyncFunString(num: Int): String {
        return "asyncFunString: $num"
    }

    // 두가지의 방법중 어떤것이 general 한 방식인지는 모르겠음

    @Async("threadPoolTaskExecutor")
    fun asyncFunStringFuture(num: Int): CompletableFuture<String> {
        return CompletableFuture.completedFuture("asyncFunString: $num")
    }

    // ListenableFuture 상속 형태
    @Async("threadPoolTaskExecutor")
    fun asyncFunStringFuture2(num: Int): Future<String> {
        return AsyncResult("asyncFunString[2]: $num")
    }

    fun syncFunString(num: Int): String {
        return "syncFunString: $num"
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}
