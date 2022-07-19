package com.example.kopring.common

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
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

    @Test
    fun cacheAutoEraseTest() {
        val cachedValue = dummyService.cachedFun3("goodall")
        cachedValue shouldBe dummyService.cachedFun3("goodall")

        Thread.sleep(2000)

        cachedValue shouldNotBe dummyService.cachedFun3("goodall")
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

    @Cacheable(value = ["cacheTest3"]) // 파라메터가 하나만 있는경우 key값을 명시해주지 않아도 된다.
    fun cachedFun3(name: String): Response {
        return Response(name)
    }

    @CacheEvict(value = ["cacheTest"])
    fun eraseCache(name: String) {
    }

    @CacheEvict(cacheNames = ["cacheTest3"], allEntries = true) // allEntries = true 필수
    @Scheduled(fixedDelay = 1 * 1000)  // 1초 만에 삭제해버린다. 스케쥴드에는 파라메터가 있어선 안된다.
    fun eraseCache() {
    }

    fun nonCachedFun(name: String): Response {
        return Response(name)
    }
}

class Response(
    val name: String
)
