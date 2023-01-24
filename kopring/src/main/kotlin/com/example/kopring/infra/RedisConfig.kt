package com.example.kopring.infra

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.io.Serializable

@Configuration
class RedisConfig(
    @Value("\${spring.redis.host}")
    val host: String,

    @Value("\${spring.redis.port}")
    val port: Int,
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<*, *> {
        return RedisTemplate<Any, Any>().apply {
            this.setConnectionFactory(redisConnectionFactory())
        }
    }

    @Bean
    fun memberRedisTemplate(): RedisTemplate<*, *> {
        return RedisTemplate<Any, Any>().apply {
            this.setConnectionFactory(redisConnectionFactory())
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = Jackson2JsonRedisSerializer(RedisObject::class.java).also {
                it.setObjectMapper(jacksonObjectMapper())
            }
        }
    }
}

data class RedisObject(
    val name: String,
    val age: Int,
) : Serializable
