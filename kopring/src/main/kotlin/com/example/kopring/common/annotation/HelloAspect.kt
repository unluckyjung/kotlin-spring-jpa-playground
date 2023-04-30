package com.example.kopring.common.annotation

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component


@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Hello

@Aspect
@Component
class HelloAspect {

    @Before("@annotation(com.example.kopring.common.annotation.Hello) || @within(com.example.kopring.common.annotation.Hello)")
    fun hello() {
        println("Hello!!")
    }
}
