package com.example.kopring.member.domain

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import javax.persistence.EntityManager

@IntegrationTest
class SoftDeleteTest(
    private val memberRepository: MemberRepository,
    private val entityManager: EntityManager
) {

    @DisplayName("entity를 이용해 soft delete 된 member는 조회되지 않는다.")
    @Test
    fun memberDeleteTest1() {
        val member1 = memberRepository.save(Member("jys"))
        member1.delete()
        memberRepository.findAll().size shouldBe 0
    }

    @DisplayName("repository를 이용해 soft delete 된 member는 조회되지 않는다.")
    @Test
    fun memberDeleteTest2() {
        val member1 = memberRepository.save(Member("jys"))
        memberRepository.delete(member1)
        memberRepository.findAll().size shouldBe 0
    }

    @DisplayName("soft delete 된 entity는 트랜잭션 내에서 id를 기준으로 조회되지 않으려면 entityManager flush를 직접 호출해야한다.")
    @Test
    fun memberDeleteTest3() {
        val member1 = memberRepository.save(Member("jys"))
        memberRepository.delete(member1)

        // 쓰기지연 저장소에 있던 내용을 db와 맞춤
        entityManager.flush()
        
        memberRepository.findByIdOrNull(member1.id) shouldBe null
    }

    @DisplayName("soft delete 된 entity는 id가 아닌 다른 조건으로 조회시, 조회되지 않는다.")
    @Test
    fun memberDeleteTest4() {
        val member1 = memberRepository.save(Member("jys"))
        memberRepository.delete(member1)

        memberRepository.findByName(member1.name) shouldBe null
    }
}
