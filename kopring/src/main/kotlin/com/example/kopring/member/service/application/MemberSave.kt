package com.example.kopring.member.service.application

import com.example.kopring.member.domain.Member
import com.example.kopring.member.domain.MemberRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class MemberSave(
    private val memberRepository: MemberRepository,
) {
    fun save(req: Member.Request): Member {
        return Member(name = req.name).let(memberRepository::save)
    }
}
