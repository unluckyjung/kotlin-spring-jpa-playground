package com.example.kopring.member.domain

import com.example.kopring.test.IntegrationTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.annotation.Commit

@IntegrationTest
class Member2RepositoryTest(
    private val member2Repository: Member2Repository,
) {
    @BeforeEach
    internal fun setUp() {
        member2Repository.save(
            Member2(
                name = "jys",
                nickName = "goodall"
            )
        )
    }

    @Test
    @Commit
    fun projectionTest() {
        val member2ProjectionData = member2Repository.findByName("jys")
        member2ProjectionData!!.nickName shouldBe "goodall"
    }
}
