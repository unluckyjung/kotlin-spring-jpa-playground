package com.example.kopring.member.service

import com.example.kopring.test.IntegrationTest
import org.junit.jupiter.api.Test

@IntegrationTest
class CallerServiceTest(
    private val callerService: CallerService,
){
    @Test
    fun test(){
        callerService.runConstructor()
        callerService.runAutowired()
    }
}
