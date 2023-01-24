package com.example.kopring.infra

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate


@IntegrationTest
class RedisConfigTest(
    private val redisTemplate: RedisTemplate<String, String>,
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

    @DisplayName("stringRedisTemplate 기반의 key")
    @Test
    fun stringRedisTest() {
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
        val hashOperations: HashOperations<String, Any, Any> = redisTemplate.opsForHash()

        val key = "hashKey"
        val hashKey = "goodall"
        val value = "yoonsung"
        hashOperations.put(key, hashKey, value)

        hashOperations.get(key, hashKey) shouldBe value

        val entries = hashOperations.entries(key)
        entries[hashKey] shouldBe value
    }

}
