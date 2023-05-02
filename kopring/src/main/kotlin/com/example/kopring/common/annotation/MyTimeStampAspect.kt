package com.example.kopring.common.annotation

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyTimestamp

@Order(1)
@Aspect
@Component
class MyTimeStampAspect {

    @Around("@annotation(com.example.kopring.common.annotation.MyTimestamp)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint) {
        logger.info("order 1 logger run!!!!")

        val sw = StopWatch()
        sw.start()
        joinPoint.proceed() // 함수실행
        sw.stop()

        logger.info("${joinPoint.signature} executed in ${sw.totalTimeMillis}ms")
        logger.info("order 1 logger end!!!!")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}

@Component
@Aspect
@Order(2)
class Order2 {
    @Around("@annotation(com.example.kopring.common.annotation.MyTimestamp)")
    fun order1(joinPoint: ProceedingJoinPoint) {
        logger.info("order 2 logger run!!!!")
        joinPoint.proceed()
        logger.info("order 2 logger end!!!!")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}


@Component
@Aspect
@Order(3)
class Order3 {
    @Around("@annotation(com.example.kopring.common.annotation.MyTimestamp)")
    fun order1(joinPoint: ProceedingJoinPoint) {
        logger.info("order 3 logger run!!!!")
        joinPoint.proceed()
        logger.info("order 3 logger end!!!!")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}


@Component
@Aspect
class NoOrder {
    @Around("@annotation(com.example.kopring.common.annotation.MyTimestamp)")
    fun order1(joinPoint: ProceedingJoinPoint) {
        logger.info("noOrder logger run!!!!")
        joinPoint.proceed()
        logger.info("noOrder logger end!!!!")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}


