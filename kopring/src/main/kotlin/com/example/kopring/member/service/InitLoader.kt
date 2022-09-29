package com.example.kopring.member.service

import com.example.kopring.member.domain.AbstractMemberRepository
import com.example.kopring.member.domain.BMember
import com.example.kopring.member.domain.Team
import com.example.kopring.member.domain.TeamRepository
import com.example.kopring.member.domain.WMember
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Order(1)
@Component
class InitLoader(
    private val teamRepository: TeamRepository,
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        Team("team1").apply {
            this.addMember(BMember("member1"))
            this.addMember(BMember("member2"))
        }.let(teamRepository::save)

        Team("team2", type = false).apply {
            this.addMember(WMember("member3"))
            this.addMember(WMember("member4"))
        }.let(teamRepository::save)
    }
}

@Order(2)
@Component
class InitLoader2(
    private val teamRepository: TeamRepository,
    private val abstractMemberRepository: AbstractMemberRepository,
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        val members = abstractMemberRepository.findAll()
        for (member in members) {
            LOGGER.info("name is : $member.name")
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(this::class.java)
    }
}
