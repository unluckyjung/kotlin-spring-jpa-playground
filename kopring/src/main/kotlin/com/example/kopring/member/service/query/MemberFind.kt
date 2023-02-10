package com.example.kopring.member.service.query

import com.example.kopring.common.support.jpa.findByIdOrThrow
import com.example.kopring.member.domain.Member
import com.example.kopring.member.domain.MemberRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class MemberFind(
    private val memberRepository: MemberRepository,
) {
    fun findById(id: Long): Member {
        return memberRepository.findByIdOrThrow(id = id)
    }
}
