package com.example.kopring.member.service

import com.example.kopring.member.domain.Member
import com.example.kopring.member.domain.MemberRepository
import org.springframework.stereotype.Component

@Component
class KtFactoryMethodPattern private constructor(
    private val memberRepository: MemberRepository,
) {
    init {
        ktFactoryMethodPattern = this
    }

    private fun getMemberList(): MutableList<Member> {
        return memberRepository.findAll()
    }

    companion object {
        lateinit var ktFactoryMethodPattern: KtFactoryMethodPattern

        fun getMemberList(): MutableList<Member> {
            return ktFactoryMethodPattern.getMemberList()
        }
    }
}
