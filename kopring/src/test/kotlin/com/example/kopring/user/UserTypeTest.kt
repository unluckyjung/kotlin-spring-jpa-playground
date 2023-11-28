package com.example.kopring.user

import com.example.kopring.test.IntegrationTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

@IntegrationTest
class UserTypeTest {

    @Test
    fun test1() {
        UserType.A_USER.run() shouldBe "AUserService goodall"
    }

    @Test
    fun test2() {
        UserType.B_USER.run() shouldBe "BUserService goodall"
    }

    @Test
    fun test3() {
        shouldThrowExactly<IllegalStateException>{
            UserType.C_USER.run()
        }.message shouldBe "설정안된 Service 가 있습니다. C_USER"
    }
}
