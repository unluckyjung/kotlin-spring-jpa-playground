package com.example.kopring.member.domain

import com.example.kopring.test.RepositoryTest
import org.junit.jupiter.api.Test

@RepositoryTest
class Member2RepositoryTest(
    private val member2Repository: Member2Repository
) {
    @Test
    fun uuidSaveTest() {
        val member = member2Repository.saveAndFlush(Member2("testUuid"))
        println("uuid: ${member.uuid}}")
    }
}