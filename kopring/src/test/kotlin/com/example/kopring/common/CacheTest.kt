package com.example.kopring.common

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
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
    @BeforeEach
    internal fun setUp() {
        Counter.init()
    }

    @DisplayName("캐싱된 결과는 같은 해시코드를 출력한다.")
    @Test
    fun cacheTest() {

        val cachedValue = dummyService.cachedFun("goodall")
        val nonCachedValue = dummyService.nonCachedFun("jys")

        println("first hash: ${cachedValue.hashCode()}")
        println("first hash: ${nonCachedValue.hashCode()}")

        for (i in 1..3) {
            cachedValue shouldBe dummyService.cachedFun("goodall")
            nonCachedValue shouldNotBe dummyService.nonCachedFun("jys")

            println("hash: ${dummyService.cachedFun("goodall").hashCode()}")
            println("hash: ${dummyService.nonCachedFun("jys").hashCode()}")
        }
    }

    @DisplayName("해시코드가 같다면 캐싱 key 가 객체여도 캐싱된 결과는 같은 해시코드를 출력한다.")
    @Test
    fun cacheObjectTest() {
        val req = Request("goodall", 31)
        val cachedValue = dummyService.cachedObjectFun(req)

        println("first hash: ${cachedValue.hashCode()}")

        for (i in 1..3) {
            cachedValue shouldBe dummyService.cachedObjectFun(req)
            cachedValue shouldNotBe Request("goodall", 31)
            println("hash: ${dummyService.cachedObjectFun(req).hashCode()}")
        }
        Counter.count shouldBe 1

        // new Request
        val newReq = Request("jys", 31)
        println("hash: ${dummyService.cachedObjectFun(newReq).hashCode()}")
        Counter.count shouldBe 2
    }

    @DisplayName("해시코드가 같다면, [캐싱키 명시] 캐싱 key 가 객체여도 캐싱된 결과는 같은 해시코드를 출력한다.")
    @Test
    fun cacheObjectTest2() {
        val req = Request("goodall", 31)
        val cachedValue = dummyService.cachedObjectFun2(req)

        println("first hash: ${cachedValue.hashCode()}")

        for (i in 1..3) {
            cachedValue shouldBe dummyService.cachedObjectFun2(req)
            println("hash: ${dummyService.cachedObjectFun2(req).hashCode()}")
        }
        Counter.count shouldBe 1
    }

    @DisplayName("새로운 객체이면, spel 기반이라 같은 값을 주어도 캐싱된 결과가 반환되지 않는다.")
    @Test
    fun cacheObjectTest3() {
        // data class 인경우, hashCode 가 같아서 캐싱가능 (예시의 Request 는 일반 class)
        val req = Request("goodall", 31)
        val cachedValue = dummyService.cachedObjectFun2(req)

        println("first hash: ${cachedValue.hashCode()}")

        for (i in 1..3) {
            val newReq = Request("goodall", 31)
            cachedValue shouldNotBe dummyService.cachedObjectFun2(newReq)
        }
        Counter.count shouldBe 4
    }

    @DisplayName("내부값을 기반으로 캐싱하면 내부값이 같은 객체를 주었을때 캐싱된다.")
    @Test
    fun cachedObjectFunByValueTest() {
        val req = Request("goodall", 31)
        val cachedValue = dummyService.cachedObjectFunByValue(req)
        println("first cached hash: ${cachedValue.hashCode()}")

        for (i in 1..3) {
            val newReq = Request("goodall", 31)
            cachedValue shouldBe dummyService.cachedObjectFunByValue(newReq)
        }
        Counter.count shouldBe 1
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

    @Cacheable(cacheNames = ["cacheTest"]) // object 로 넘겨도 캐싱된다. (해시코드 기반)
    fun cachedObjectFun(req: Request): Response {
        Counter.countUp()
        return Response(req.name)
    }

    @Cacheable(cacheNames = ["cacheTest"], key = "#req") // key 값을 몇시하고, object 로 넘겨도 캐싱된다. (해시 코드기반)
    fun cachedObjectFun2(req: Request): Response {
        Counter.countUp()
        return Response(req.name)
    }

    @Cacheable(cacheNames = ["cacheTest3"], key = "#req.age + #req.name") // object 안의 내부키 기반 캐싱
    fun cachedObjectFunByValue(req: Request): Response {
        Counter.countUp()
        return Response(req.name)
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
    val name: String,
    val age: Int,
)

object Counter {
    var count = 0
        private set

    fun init() {
        count = 0
    }

    fun countUp() {
        count++
    }
}
