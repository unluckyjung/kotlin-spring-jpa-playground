package com.example.kopring.member.service

import com.example.kopring.member.domain.MyMember
import com.example.kopring.member.domain.Team
import com.example.kopring.member.domain.TeamRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Order(1)
@Component
class InitLoader(
    private val teamRepository: TeamRepository,
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {
        Team("team1").apply {
            this.addMember(MyMember("member1"))
            this.addMember(MyMember("member2"))
        }.let(teamRepository::save)

        Team("team2").apply {
            this.addAllMember(listOf(MyMember("member3"), MyMember("member4")))
        }.let(teamRepository::save)
    }
}

@Order(2)
@Component
class InitLoader2(
    private val teamRepository: TeamRepository,
) : ApplicationRunner {
    @Transactional
    override fun run(args: ApplicationArguments?) {
        val team = teamRepository.findAll().firstOrNull()
        teamRepository.delete(team!!)
    }
}
