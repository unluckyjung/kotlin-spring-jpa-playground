package com.example.kopring.common.util

import ch.qos.logback.classic.Level.ERROR
import ch.qos.logback.classic.Level.INFO
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory


internal class MyLoggerTestDummyClass {

    fun infoLog(message: String) {
        logger.info("$message info log")
    }

    fun errorLog(message: String) {
        logger.error("$message error log")
    }

    companion object {
        private val logger = logger()
    }
}


class LoggerFactoryTest {

    @DisplayName("MyLoggerFactory 안의 logger 로 Info 로그를 찍히면 로그 히스토리가 남는다.")
    @Test
    fun testInfoLogger() {
        val logger = LoggerFactory.getLogger(MyLoggerTestDummyClass::class.java) as Logger
        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        logger.addAppender(listAppender)

        val dummyClass = MyLoggerTestDummyClass()

        dummyClass.infoLog("yoonsung")
        listAppender.list.filter {
            (it.level == INFO) && (it.message == "yoonsung info log")
        }.size shouldBe 1

        listAppender.stop()
    }

    @DisplayName("MyLoggerFactory 안의 logger 로 error 로그를 찍히면 로그 히스토리가 남는다.")
    @Test
    fun testErrorLogger() {
        val logger = LoggerFactory.getLogger(MyLoggerTestDummyClass::class.java) as Logger
        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        logger.addAppender(listAppender)

        val dummyClass = MyLoggerTestDummyClass()

        dummyClass.errorLog("unluckyjung")

        listAppender.list.filter {
            (it.level == ERROR) && (it.message == "unluckyjung error log")
        }.size shouldBe 1

        listAppender.stop()
    }
}
