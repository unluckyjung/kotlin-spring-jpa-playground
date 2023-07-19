package com.example.kopring.member.domain

import com.example.kopring.common.support.jpa.findByIdOrThrow
import com.example.kopring.member.event.DomainEventPublisher
import com.example.kopring.member.service.application.MemberSave
import com.example.kopring.member.service.application.MemberSaveEvent
import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.test.context.transaction.TestTransaction
import java.util.concurrent.TimeUnit

@IntegrationTest
class MemberDomainEventTest(
    val memberSave: MemberSave,
    val memberRepository: MemberRepository,
    @Qualifier("threadPoolTaskExecutor")
    private val threadPoolTaskExecutor: TaskExecutor
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

        val executor = threadPoolTaskExecutor as ThreadPoolTaskExecutor
        executor.threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS)

        val afterMember = memberRepository.findByIdOrThrow(member.id)
        afterMember.name shouldBe afterName
    }
}
