package com.example.kopring.member.domain

import com.example.kopring.test.RepositoryTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@RepositoryTest
class MemberRepositoryDeleteTest(
    private val memberRepository: MemberRepository
) {

    @BeforeEach
    internal fun setUp() {
        memberRepository.saveAllAndFlush(
            mutableListOf(
                Member("goodall"),
                Member("unluckyjung"),
                Member("jys"),
            )
        )
    }

    @Test
    fun deleteAll() {
        memberRepository.deleteAll()
        memberRepository.flush()
    }

    @Test
    fun deleteAllInBatch() {
        memberRepository.deleteAllInBatch()
        memberRepository.flush()
    }
}
