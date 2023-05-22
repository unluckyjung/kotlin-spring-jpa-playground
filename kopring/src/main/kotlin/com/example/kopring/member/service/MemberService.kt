package com.example.kopring.member.service

import com.example.kopring.member.domain.Member
import com.example.kopring.member.service.application.MemberSave
import com.example.kopring.member.service.query.MemberFind
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberSave: MemberSave,
    private val memberFind: MemberFind,
) {
    fun find(id: Long): Member.Response {
        return memberFind.findById(id = id).run {
            Member.Response(
                id = this.id,
                name = this.name
            )
        }
    }

    fun save(req: Member.Request): Member.Response {
        return memberSave.save(req).run {
            Member.Response(
                id = this.id,
                name = this.name
            )
        }
    }

    @Transactional(propagation = Propagation.NEVER)
    fun noTransactionFun(){

    }
}
