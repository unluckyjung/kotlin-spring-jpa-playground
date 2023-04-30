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
}


@Service
@Hello
class AopTestService {
    fun run1() {
        println("run1 running...")
    }
}
