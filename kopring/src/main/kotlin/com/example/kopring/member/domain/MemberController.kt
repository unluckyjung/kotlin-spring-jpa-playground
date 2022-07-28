package com.example.kopring.member.domain

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional

@RestController
@RequestMapping("api/v1")
class MemberController(
    private val memberRepository: MemberRepository
) {

    @PostMapping("/save")
    @Transactional
    fun save() {
        memberRepository.save(Member("jys"))
    }

    @DeleteMapping("delete")
    @Transactional
    fun delete() {
        memberRepository.deleteAllInBatch()
    }

    @DeleteMapping("delete1")
    @Transactional
    fun delete1() {
        val member = memberRepository.findByName("jys")
        memberRepository.delete(member!!)
    }
}
