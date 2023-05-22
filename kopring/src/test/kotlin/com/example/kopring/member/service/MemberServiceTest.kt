package com.example.kopring.member.service

import io.kotest.assertions.throwables.shouldThrowExactly
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.IllegalTransactionStateException
import org.springframework.transaction.annotation.Transactional

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
internal class MemberServiceTest(
    private val memberService: MemberService,
) {

    @Transactional
    @Test
    fun noTransactionFun() {
        shouldThrowExactly<IllegalTransactionStateException> {
            memberService.noTransactionFun()
        }
    }
}
