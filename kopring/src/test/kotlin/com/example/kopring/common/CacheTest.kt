package com.example.kopring.common

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.cache.annotation.CacheEvict
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

    @Test
    fun cacheTest2() {

        val cachedValue = dummyService.cachedFun2("goodall", 30)
        cachedValue shouldBe dummyService.cachedFun2("goodall", 30)
    }

    @Test
    fun cacheTest3() {
        val cachedValue = dummyService.cachedFun("goodall")
        cachedValue shouldBe dummyService.cachedFun("goodall")

        dummyService.eraseCache("goodall")
        cachedValue shouldNotBe dummyService.cachedFun("goodall")
    }
}

@Service
class DummyService {

    //    @Cacheable(value = ["cacheTest"], key = "#name")
    @Cacheable(value = ["cacheTest"]) // 파라메터가 하나만 있는경우 key값을 명시해주지 않아도 된다.
    fun cachedFun(name: String): Response {
        return Response(name)
    }

    @Cacheable(value = ["cacheTest2"], key = "#name + #age")
    fun cachedFun2(name: String, age: Int): Response {
        return Response(name)
    }

    @CacheEvict(value = ["cacheTest"])
    fun eraseCache(name: String) {
    }

    fun nonCachedFun(name: String): Response {
        return Response(name)
    }
}

class Response(
    val name: String
)
