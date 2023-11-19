package com.example.kopring.common.support.redis

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

@IntegrationTest
internal class RedisKeyValueStoreTest(
    private val redisKeyValueStore: RedisKeyValueStore,
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    @BeforeEach
    internal fun setUp() {
        flushRedis()
    }

    @AfterEach
    internal fun tearDown() {
        redisTemplate.connectionFactory?.connection?.flushAll()
    }

    private fun flushRedis() {
        redisTemplate.connectionFactory?.connection?.flushAll()
    }

    @DisplayName("[] 연산자를 이용해서 get, set 이 가능하다.")
    @Test
    fun operatorTest() {
        val key = "yoonsung"
        val value = "goodall"
        redisKeyValueStore[key] = value

        val result = redisKeyValueStore[key, String::class.java]
        result shouldBe value
    }

    @DisplayName("object 를 String 으로 변환해서 저장, 조회할 수 있다.")
    @Test
    fun ttlTest() {
        val key = "yoonsung"
        val value = UnluckyJung(name = "goodall", age = 30)
        redisKeyValueStore[key] = value

        val result = redisKeyValueStore[key, UnluckyJung::class.java]
        result shouldBe value
    }

    @DisplayName("key 에 따른 3초후 삭제 옵션을 주면, 3초후 조회되지 않는다.")
    @Test
    fun redisDeleteTTLTest() {
        val key = "expireKey"
        val value = "goodall"

        redisKeyValueStore.save(
            key = key,
            value = value,
            timeOut = RedisKeyValueTimeOut(time = 3L, TimeUnit.SECONDS),
        )

        redisKeyValueStore[key, String::class.java] shouldBe value

        Thread.sleep(3500)
        redisKeyValueStore[key, String::class.java] shouldBe null
    }
}

data class UnluckyJung(
    val name: String,
    val age: Int,
)
