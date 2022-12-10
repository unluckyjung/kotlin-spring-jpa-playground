package com.example.kopring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

@Configuration
@EnableAsync
class AsyncConfig : AsyncConfigurer {
    override fun getAsyncExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            this.corePoolSize = 5
            this.maxPoolSize = 20
            this.setQueueCapacity(50)
            this.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
            this.setWaitForTasksToCompleteOnShutdown(true)
            this.setAwaitTerminationSeconds(60)
            this.setThreadNamePrefix("default task executor")
            this.initialize() // @Bean 어노테이션이 없어, 호출이 필수
        }
    }

    @Bean
    fun customThreadPoolTaskExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            this.corePoolSize = 5 // 기본 스레드 풀
            this.maxPoolSize = 20 // 최대 스레드 풀 (큐에도 다 찬 경우에 해당 사이즈로 작동)
            this.setQueueCapacity(50) // 초과 요청을 담는 큐의 개수
            this.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy()) // 문제가 발생하는 경우 해당스레드에서 다시 처리 (CallerRunsPolicy)
            this.setWaitForTasksToCompleteOnShutdown(true) // shutdown 당해도 다른 작업 이어서 처리
            this.setAwaitTerminationSeconds(60) // 최대 60초 대기
            this.setThreadNamePrefix("unluckyjung task executor") // 접두사
        }
    }
}