package com.example.kopring.common.annotation

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyTimestamp

@Aspect
@Component
class MyTimeStampAspect {

    @Around("@annotation(com.example.kopring.common.annotation.MyTimestamp)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint) {
        val sw = StopWatch()
        sw.start()
        joinPoint.proceed() // 함수실행
        sw.stop()

        logger.info("${joinPoint.signature} executed in ${sw.totalTimeMillis}ms")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}
