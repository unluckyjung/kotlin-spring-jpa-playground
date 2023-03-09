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
    fun tx1() {
        saveService.tx1()
        memberRepository.findAll().size shouldBe 2

        // 조회되는 상황이 다르다.
        teamRepository.findAll().size shouldBe 1
    }
}

@Service
class SaveService(
    private val memberRepository: MemberRepository,
    private val requiredNewService: RequiredNewService,
) {

    @Transactional
    fun tx1() {

        // id 를 넣어서 저장하는 경우 조회안됌
//        val member = memberRepository.save(Member(id = 100, name = "AA"))
        val member = memberRepository.save(Member(name = "AA"))

        requiredNewService.tx2(member)

        // 여기서 id를 넣는것은 상관없음.
        memberRepository.save(Member(name = "CC"))
    }
}


@Service
class RequiredNewService(
    private val teamRepository: TeamRepository,
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun tx2(member: Member) {
        println("================${member.id}==================")

        // 여기서 id 를 넣는것은 상관이없음.
        teamRepository.save(Team(name = "BB"))
    }
}
