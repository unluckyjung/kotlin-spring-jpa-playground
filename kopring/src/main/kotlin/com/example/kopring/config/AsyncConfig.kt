package com.example.kopring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
@EnableAsync
// AsyncConfigure 상속은 선택사항
class AsyncConfig {

    @Bean
    fun threadPoolTaskExecutor(): ThreadPoolTaskExecutor {
        return ThreadPoolTaskExecutor().apply {
            this.corePoolSize = 5   // 기본 스레드 풀
            this.maxPoolSize = 20   // 최대 스레드 풀
            this.setQueueCapacity(50)   // 초과 요청을 담는 큐의 개수
            this.setThreadNamePrefix("goodall") // 접두사
        }
    }
}
