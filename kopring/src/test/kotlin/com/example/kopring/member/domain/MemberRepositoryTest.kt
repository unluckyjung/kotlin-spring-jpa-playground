package com.example.kopring.member.domain

import com.example.kopring.test.RepositoryTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@RepositoryTest
class MemberRepositoryTest(
    private val memberRepository: MemberRepository
) {

    lateinit var member: Member

    @BeforeEach
    internal fun setUp() {
        member = memberRepository.save(
            Member("goodall")
        )
    }

    @DisplayName("멤버가 최초로 저장되면, id로 조회했을때 조회된다.")
    @Test
    fun memberGetByIdTest() {
        val member1 = memberRepository.getByMemberId(member.id!!)
        member1 shouldBe member
    }

    @DisplayName("존재하지 않는 id로 조회하면, 예외가 발생한다.")
    @Test
    fun memberGetByIdFailTest() {
        assertThrows<NoSuchElementException> {
            memberRepository.getByMemberId(100L)
        }
    }
}
