package com.example.kopring.member

import com.example.kopring.member.domain.*
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Order(1)
@Component
class InitLoader2(
    private val memberRepository: MemberRepository,
) : ApplicationRunner {
    @Transactional
    override fun run(args: ApplicationArguments?) {
        memberRepository.save(
            Member(name = "jys", priority = 3, nickName = "fortune")
        )

        memberRepository.save(
            Member(name = "jys", priority = 1, nickName = "goodall")
        )
    }
}

