package com.example.kopring.member.domain

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@IntegrationTest
class TransactionTest(
    private val saveService: SaveService,
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
) {
    @Transactional
    @Test
    fun test() {
        saveService.test1()
        memberRepository.findAll().size shouldBe 2

        // 왜 조회가 되는것이지?
        teamRepository.findAll().size shouldBe 1
    }
}

@Service
class SaveService(
    private val memberRepository: MemberRepository,
    private val requiredNewService: RequiredNewService,
) {

    @Transactional
    fun test1() {
        val member = memberRepository.save(Member(name = "AA"))

        requiredNewService.test2(member)
        memberRepository.save(Member(name = "CC"))
    }
}


@Service
class RequiredNewService(
    private val teamRepository: TeamRepository,
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun test2(member: Member) {
        println("================${member.id}==================")

        teamRepository.save(Team(name = "BB"))
    }
}
