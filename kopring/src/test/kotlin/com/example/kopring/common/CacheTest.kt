package com.example.kopring.common

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@IntegrationTest
class CacheTest(
    val dummyService: DummyService
) {

    @Test
    fun cacheTest() {

        val cachedValue = dummyService.cachedFun("goodall")
        val nonCachedValue = dummyService.nonCachedFun("jys")

//        println("first hash: ${cachedValue.hashCode()}")
//        println("first hash: ${nonCachedValue.hashCode()}")

        for (i in 1..3) {
            cachedValue shouldBe dummyService.cachedFun("goodall")
            nonCachedValue shouldNotBe dummyService.nonCachedFun("jys")

//            println("hash: ${dummyService.cachedFun("goodall").hashCode()}")
//            println("hash: ${dummyService.nonCachedFun("jys").hashCode()}")
        }
    }
}

@Service
class DummyService {

    @Cacheable(value = ["cacheTest"], key = "#name")
    fun cachedFun(name: String): Response {
        return Response(name)
    }

    fun nonCachedFun(name: String): Response {
        return Response(name)
    }
}

class Response(
    val name: String
)
