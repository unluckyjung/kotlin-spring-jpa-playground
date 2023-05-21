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

@Component
class KtFactoryMethodPattern2 private constructor(
    private val memberRepository: MemberRepository,
) {
    init {
        KtFactoryMethodPattern2.memberRepository = memberRepository
    }

    companion object {
        // 의존성 주입 받아야 하는 종류를 이렇게 처리가능
        private lateinit var memberRepository: MemberRepository

        fun getMemberList(): MutableList<Member> {
            return memberRepository.findAll()
        }
    }
}

