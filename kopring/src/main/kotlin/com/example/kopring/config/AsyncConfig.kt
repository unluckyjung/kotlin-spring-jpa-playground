package com.example.kopring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.ThreadPoolExecutor

@Configuration
@EnableAsync
// AsyncConfigure 상속은 선택사항
class AsyncConfig {

    @Bean
    fun threadPoolTaskExecutor(): ThreadPoolTaskExecutor {
        return ThreadPoolTaskExecutor().apply {
            this.corePoolSize = 5 // 기본 스레드 풀
            this.maxPoolSize = 20 // 최대 스레드 풀 (큐에도 다 찬 경우에 해당 사이즈로 작동)
            this.setQueueCapacity(50) // 초과 요청을 담는 큐의 개수
            this.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy()) // 문제가 발생하는 경우 해당스레드에서 다시 처리 (CallerRunsPolicy)
            this.setWaitForTasksToCompleteOnShutdown(true) // shutdown 당해도 다른 작업 이어서 처리
            this.setAwaitTerminationSeconds(60) // 최대 60초 대기
            this.setThreadNamePrefix("goodall") // 접두사
        }
    }
}
