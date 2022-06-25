package com.example.kopring.member.config

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ValueComponentTest(
    @Autowired
    val valueComponent: ValueComponent
) {
    @Test
    fun valueTest() {
        // application.yml
        valueComponent.name shouldBe "unluckyjung"
        valueComponent.age shouldBe 30
        valueComponent.bookList shouldContainExactly listOf("A", "B", "C")
    }
}
