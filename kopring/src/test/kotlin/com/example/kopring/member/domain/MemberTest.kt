package com.example.kopring.member.domain

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import javax.validation.Validation

class MemberTest {

    @DisplayName("Request 테스트")
    @Nested
    class RequestTest {
        private val validator = Validation.buildDefaultValidatorFactory().validator

        @DisplayName("이름이 공백이면 Validation 의 validator 에 예외가 쌓인다.")
        @Test
        fun nameBlankFailTest() {

            val memberReq = Member.Request(" ")
            val violations = validator.validate(memberReq)

            violations.size shouldBe 1

            for (failMsg in violations) {
                failMsg.message shouldMatch ("이름은 공백으로 이루어져있을 수 없습니다.")
            }
        }

        @DisplayName("이름이 공백으로 이루어있지 않으면, 예외가 쌓이지 않는다.")
        @Test
        fun nameBlankTest() {

            val memberReq = Member.Request("jys")
            val violations = validator.validate(memberReq)

            violations.size shouldBe 0
        }
    }
}
