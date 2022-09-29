package com.example.kopring.member.service

import com.example.kopring.member.domain.MyMember
import com.example.kopring.member.domain.Team
import com.example.kopring.member.domain.TeamRepository
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
            this.addMember(MyMember("member1"))
            this.addMember(MyMember("member2"))
        }.let(teamRepository::save)
    }
}


@Order(2)
@Component
class InitLoader2(
    private val teamRepository: TeamRepository,
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        val team = teamRepository.findAll().firstOrNull()
        team!!.members.removeAt(1)
    }
}
