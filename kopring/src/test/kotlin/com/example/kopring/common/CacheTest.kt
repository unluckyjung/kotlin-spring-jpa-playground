package com.example.kopring.common

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@IntegrationTest
class CacheTest(
    private val dummyService: DummyService,
) {

    private val sameKey = "unluckyjung"

    @DisplayName("같은 캐시키 값을 넣으면, 다른 테스트에 영향을 줄 수 있다.")
    @Test
    fun test1() {
        val result = dummyService.cachedFun(sameKey, "goodall")
        result shouldBe "goodall"
    }


    @DisplayName("같은 캐시키 값을 넣으면, 다른 테스트에 영향을 줄 수 있다.")
    @Test
    fun test2() {
        val result = dummyService.cachedFun(sameKey, "yoonsung")
        result shouldBe "yoonsung"
    }
}

@Service
class DummyService {

    @Cacheable(value = ["cacheTest"], key = "#key")
    fun cachedFun(key: String, other: String): String {
        return other
    }
}


