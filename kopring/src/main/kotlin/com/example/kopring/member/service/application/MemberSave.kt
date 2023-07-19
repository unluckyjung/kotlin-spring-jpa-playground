package com.example.kopring.member.service.application

import com.example.kopring.common.support.jpa.findByIdOrThrow
import com.example.kopring.member.domain.Member
import com.example.kopring.member.domain.MemberRepository
import com.example.kopring.member.event.DomainEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Transactional
@Component
class MemberSave(
    private val memberRepository: MemberRepository,
) {
    fun save(req: Member.Request): Member {
        val member = Member(name = req.name).let(memberRepository::save)

        // member 가 저장했을때 이벤트 발생
        DomainEventPublisher.publishEvent(
            MemberSaveEvent(memberId = member.id)
        )

        return member
    }
}

data class MemberSaveEvent(
    val memberId: Long,
)

@Component
class MemberSaveEventHandler(
    private val memberRepository: MemberRepository,
) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)  // 앞 작업에서의 커밋이 종료된뒤 처리
    @Transactional
    @Async("threadPoolTaskExecutor")
    fun onSave(memberSaveEvent: MemberSaveEvent) {

        memberRepository.findByIdOrThrow(memberSaveEvent.memberId).run {
            this.changeName(name = "${this.name} event")
        }
    }
}
