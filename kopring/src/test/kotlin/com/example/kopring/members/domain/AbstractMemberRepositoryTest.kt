package com.example.kopring.members.domain

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@IntegrationTest
internal class AbstractMemberRepositoryTest(
    private val abstractMemberRepository: AbstractMemberRepository,
    private val bMemberRepository: BMemberRepository,
    private val wMemberRepository: WMemberRepository,
) {
    @DisplayName("저장은 DTYPE 타입 구분없이 가능하다. 조회시에는 레포지토리 별로 DTYPE 조건을 보고 쿼리 필터가 되어 조회된다.")
    @Test
    fun saveTest() {
        val whiteMemberName = "goodall"
        val blackMemberName = "unluckyjung"

        abstractMemberRepository.save(WMember(name = whiteMemberName))
        abstractMemberRepository.save(BMember(name = blackMemberName))

        val result = abstractMemberRepository.findAll()
        result.size shouldBe 2
        result[0].name shouldBe whiteMemberName
        result[1].name shouldBe blackMemberName


        val wResult = wMemberRepository.findAll()
        wResult.size shouldBe 1
        wResult[0].name shouldBe whiteMemberName
        wResult[0].isBlackList() shouldBe false


        val bResult = bMemberRepository.findAll()
        bResult.size shouldBe 1
        bResult[0].name shouldBe blackMemberName
        bResult[0].isBlackList() shouldBe true
    }

    @DisplayName("sealed class 을 이용해 타입 구분을 쉽게할 수 있다.")
    @Test
    fun sealedClassType() {
        val whiteMemberName = "goodall"
        val blackMemberName = "unluckyjung"

        abstractMemberRepository.save(WMember(name = whiteMemberName))
        abstractMemberRepository.save(BMember(name = blackMemberName))

        val result = abstractMemberRepository.findAll()

        result.forEach {
            when (it) {
                is WMember -> {
                    it.name shouldBe whiteMemberName
                    it.isBlackList() shouldBe false
                }

                is BMember -> {
                    it.name shouldBe blackMemberName
                    it.isBlackList() shouldBe true
                }
            }
        }
    }
}

