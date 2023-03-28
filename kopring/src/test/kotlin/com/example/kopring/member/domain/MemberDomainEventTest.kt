package com.example.kopring.member.domain

import com.example.kopring.common.support.jpa.findByIdOrThrow
import com.example.kopring.member.event.DomainEventPublisher
import com.example.kopring.member.service.application.MemberSave
import com.example.kopring.member.service.application.MemberSaveEvent
import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.context.transaction.TestTransaction

@IntegrationTest
class MemberDomainEventTest(
    val memberSave: MemberSave,
    val memberRepository: MemberRepository,
) {

    @DisplayName("도메인 이벤트 발생시키면, 이벤트 리스너에서 받아서 처리된다.")
    @Test
    fun memberSaveDomainEventTest() {
        val beforeName = "yoonsung"
        val afterName = "$beforeName event"

        TestTransaction.flagForCommit()

        val member = memberSave.save(Member.Request(name = beforeName))
        memberRepository.findByIdOrThrow(member.id).name shouldBe beforeName

        // 트랜잭션 종료, @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) 수행시킴
        TestTransaction.end()

        val afterMember = memberRepository.findByIdOrThrow(member.id)
        afterMember.name shouldBe afterName

        // 메모리 DB가 아닌경우 새로운 트랜잭션에서의 저장이라, 롤백이 안되므로 명시적으로 삭제해줘야함.
        memberRepository.delete(afterMember)
    }
}
