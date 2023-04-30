package com.example.kopring.common.annotation

import com.example.kopring.test.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.stereotype.Service

@IntegrationTest
class AopAnnotationTest(
    private val aopTestService: AopTestService,
) {
    @Test
    fun run1Test() {
        aopTestService.run1()
    }

    @Test
    fun timeStamp1Test() {
        aopTestService.timeStampRun1()
    }
}


@Service
class AopTestService {
    @Hello
    fun run1() {
        println("run1 running...")
    }

    @MyTimestamp
    fun timeStampRun1() {
        println("timeStampRun1 running...start")
        Thread.sleep(2000)
        println("timeStampRun1 running...end")
    }
}
