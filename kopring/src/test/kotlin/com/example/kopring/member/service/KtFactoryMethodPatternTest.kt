package com.example.kopring.member.service

import com.example.kopring.member.domain.Member
import com.example.kopring.member.domain.MemberRepository
import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

@IntegrationTest
class KtFactoryMethodPatternTest(
    private val memberRepository: MemberRepository,
) {
    @Test
    fun factoryMethodPatternTest() {
        Member(name = "jys").run(memberRepository::save)

        KtFactoryMethodPattern.getMemberList().size shouldBe 1
    }
}
