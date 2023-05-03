package com.example.kopring.test

import com.example.kopring.member.domain.TeamRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

@IntegrationTest
@TruncateAllTables
class TruncateTablesTest(
    private val teamRepository: TeamRepository,
) {
    @Test
    fun truncateTest() {
        teamRepository.findAll() shouldBe emptyList()
    }
}

@IntegrationTest
class TruncateTablesTest2(
    private val teamRepository: TeamRepository,
) {
    @Test
    fun truncateTest() {
        teamRepository.findAll().size shouldNotBe 0
    }
}
