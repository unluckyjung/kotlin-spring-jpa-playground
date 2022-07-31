package com.example.kopring.common

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
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

        val cachedValue = dummyService.cachedFun2("jys", 30, "goodall")
        cachedValue shouldBe dummyService.cachedFun2("jys", 30, "goodall")
    }

    @Test
    fun cacheTest3() {
        val cachedValue = dummyService.cachedFun("goodall")
        cachedValue shouldBe dummyService.cachedFun("goodall")

        dummyService.eraseCache1("goodall")
        cachedValue shouldNotBe dummyService.cachedFun("goodall")
    }

    @Test
    fun cachePut1Test() {
        val cachedValue = dummyService.cachedFun("goodall")
        cachedValue shouldBe dummyService.cachedFun("goodall")

        dummyService.cachePut1("goodall")
        cachedValue shouldNotBe dummyService.cachedFun("goodall")
    }

    @Test
    fun cachePut2Test() {
        val cachedValue = dummyService.cachedFun("admin")
        cachedValue shouldBe dummyService.cachedFun("admin")

        dummyService.cachePut2("admin")
        cachedValue shouldBe dummyService.cachedFun("admin")
    }

    @Test
    fun cacheAutoEraseTest() {
        val cachedValue = dummyService.cachedFun3("goodall")
        cachedValue shouldBe dummyService.cachedFun3("goodall")

        Thread.sleep(2000)

        cachedValue shouldNotBe dummyService.cachedFun3("goodall")
    }
}

//@CacheConfig(cacheNames = ["myCache"])
@Service
class DummyService {

    //    @Cacheable(value = ["cacheTest"], key = "#name")
    @Cacheable(cacheNames = ["cacheTest"]) // 파라메터가 하나만 있는경우 key값을 명시해주지 않아도 된다.
    fun cachedFun(name: String): Response {
        return Response(name)
    }

    @Cacheable(value = ["cacheTest2"], key = "#name + #age")
    fun cachedFun2(name: String, age: Int, nickName: String): Response {
        return Response(name)
    }

    @Cacheable(value = ["cacheTest3"]) // 파라메터가 하나만 있는경우 key값을 명시해주지 않아도 된다.
    fun cachedFun3(name: String): Response {
        return Response(name)
    }

    @Cacheable(value = ["cacheTest4"], key = "#req.name")
    fun cachedFun4(req: Request): Response {
        return Response(req.name)
    }

    @CachePut(value = ["cacheTest"])
    fun cachePut1(name: String) {
    }

    @CachePut(value = ["cacheTest"], condition = "#name != 'admin'")
    fun cachePut2(name: String) {
    }

    @CacheEvict(value = ["cacheTest"])
    fun eraseCache1(name: String) {
    }

    @CacheEvict(cacheNames = ["cacheTest3"], allEntries = true) // allEntries = true 필수
    @Scheduled(fixedDelay = 1 * 1000)  // 1초 만에 삭제해버린다. 스케쥴드에는 파라메터가 있어선 안된다.
    fun eraseCache1() {
    }

    fun nonCachedFun(name: String): Response {
        return Response(name)
    }
}

class Response(
    val name: String
)

class Request(
    val name: String
)
