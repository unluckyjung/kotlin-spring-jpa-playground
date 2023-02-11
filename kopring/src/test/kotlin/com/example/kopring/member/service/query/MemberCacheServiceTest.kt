package com.example.kopring.member.service.query

import com.example.kopring.member.domain.Member
import com.example.kopring.member.domain.MemberRepository
import com.example.kopring.member.service.MemberService
import com.example.kopring.test.IntegrationTest
import com.ninjasquad.springmockk.SpykBean
import io.kotest.matchers.shouldBe
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@IntegrationTest
class MemberCacheServiceTest(
    private val memberCacheService: MemberCacheService,
    private val memberRepository: MemberRepository,
) {
    @SpykBean
    private lateinit var memberService: MemberService

    @DisplayName("캐싱 함수를 통해 호출하는 경우, 같은 아이디로 호출시 캐싱된 결과가 반환된다.")
    @Test
    fun cacheTest() {
        val member = memberRepository.save(Member(name = "unluckyjung"))

        repeat(3) {
            val result = memberCacheService.findByUseCache(member.id)
            result.name shouldBe member.name
        }

        verify(exactly = 1) {
            memberService.find(member.id)
        }
    }

    @DisplayName("캐싱 함수를 통해 호출하지 않는 경우, 같은 아이디로 호출시 캐싱된 결과가 반환되지 않는다.")
    @Test
    fun cacheTest2() {
        val member = memberRepository.save(Member(name = "unluckyjung"))

        repeat(3) {
            val result = memberCacheService.findByUseNoCache(member.id)
            result.name shouldBe member.name
        }

        verify(exactly = 3) {
            memberService.find(member.id)
        }
    }
}
