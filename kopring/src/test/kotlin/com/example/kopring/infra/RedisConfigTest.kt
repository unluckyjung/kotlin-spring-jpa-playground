package com.example.kopring.infra

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import java.util.concurrent.TimeUnit

@IntegrationTest
class RedisConfigTest(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectRedisTemplate: RedisTemplate<String, RedisObject>,
    private val hashRedisTemplate: RedisTemplate<Any, Any>,
    private val hashObjectTemplate: RedisTemplate<String, RedisObject>,
    private val stringRedisTemplate: StringRedisTemplate,
) {
    @DisplayName("string 기반의 key")
    @Test
    fun stringCacheTest() {
        val valueOperations = redisTemplate.opsForValue()
        val key = "stringKey"
        val value = "goodall"

        valueOperations[key] = "goodall"
        valueOperations[key] shouldBe value
    }

    @DisplayName("key: string, value: ObjectTest")
    @Test
    fun stringCacheObjectTest() {
        val valueOperations: ValueOperations<String, RedisObject> = objectRedisTemplate.opsForValue()
        val key = "string-object-key"
        val objectValue = RedisObject("yoonsung", age = 30)

        valueOperations[key] = objectValue

        // 조회 과정에서 문제발생
        valueOperations[key] shouldBe objectValue
    }

    @DisplayName("stringRedisTemplate 기반의 key")
    @Test
    fun stringRedisObjectTest() {
        val valueOperations = stringRedisTemplate.opsForValue()
        val key = "stringKey"
        val value = "goodall"

        valueOperations[key] = "goodall"
        valueOperations[key] shouldBe value
    }


    @DisplayName("set 기반의 key")
    @Test
    fun setCacheTest() {
        val valueOperations = redisTemplate.opsForSet()
        val key = "setKey"

        valueOperations.add(key, "unluckyjung", "goodall")

        valueOperations.members(key)?.shouldContain("unluckyjung")
        valueOperations.members(key)?.size shouldBe 2
    }

    @DisplayName("hash 기반의 key")
    @Test
    fun hashCacheTest() {
//        val hashOperations: HashOperations<String, String, String> = redisTemplate.opsForHash()
        val hashOperations = hashRedisTemplate.opsForHash<String, String>()

        val key = "hashKey"
        val hashKey = "goodall"
        val value = "yoonsung"
        hashOperations.put(key, hashKey, value)

        hashOperations.get(key, hashKey) shouldBe value

        val entries = hashOperations.entries(key)

        entries[hashKey] shouldBe value
    }

    @DisplayName("hash 기반의 string-object")
    @Test
    fun hashCacheObjectTest() {
        val key = "hash-object-key"
        val hashKey = "goodall"
        val objectValue = RedisObject("yoonsung", age = 30)

        val hashOperations = hashObjectTemplate.opsForHash<String, RedisObject>()
        hashOperations.put(key, hashKey, objectValue)
        hashOperations.get(key, hashKey) shouldBe objectValue

        val entries = hashOperations.entries(key)

        entries[hashKey] shouldBe objectValue
    }


    @DisplayName("key 에 따른 3초후 삭제 옵션을 주면, 3초후 조회되지 않는다.")
    @Test
    fun stringRedisDeleteWaitTest() {
        val valueOperations = stringRedisTemplate.opsForValue()
        val key = "expireKey"
        val value = "goodall"

        valueOperations[key] = value

        // 5초뒤 삭제
        redisTemplate.expire(key, 3, TimeUnit.SECONDS)
        valueOperations[key] shouldBe value

        Thread.sleep(3500)
        valueOperations[key] shouldBe null
    }
}
