package com.example.kopring.common.support.redis

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisKeyValueStore(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: ObjectMapper,
) {

    fun save(key: String, value: Any, timeOut: RedisKeyValueTimeOut? = null) {

        timeOut?.let {
            redisTemplate.opsForValue().set(
                key,
                objectMapper.writeValueAsString(value),
                timeOut.time,
                timeOut.timeUnit
            )
        } ?: run {
            redisTemplate.opsForValue().set(
                key,
                objectMapper.writeValueAsString(value),
            )
        }
    }

    fun delete(key: String){
        redisTemplate.delete(key)
    }

    fun <T> getByKey(key: String, clazz: Class<T>): T? {
        val result = redisTemplate.opsForValue()[key].toString()
        return if (result.isEmpty()) null
        else {
            return objectMapper.readValue(result, clazz)
        }
    }

    operator fun <T> get(key: String, clazz: Class<T>): T? {
        return getByKey(key = key, clazz = clazz)
    }

    operator fun set(key: String, value: Any) {
        return save(key = key, value = value)
    }
}

class RedisKeyValueTimeOut(
    val time: Long,
    val timeUnit: TimeUnit,
)
