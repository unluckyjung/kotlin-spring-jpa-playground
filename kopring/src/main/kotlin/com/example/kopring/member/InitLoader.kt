package com.example.kopring.member

import com.example.kopring.member.domain.*
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Order(1)
class InitLoader(
    private val teamRepository: TeamRepository,
    private val myMemberRepository: MyMemberRepository,
    private val midRepository: MidRepository,
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {

        val team = Team("team1").apply {}.let(teamRepository::save)
        val myMember = MyMember("myMembmer1").apply {}.let(myMemberRepository::save)

        val mid = Mid("mid1").apply {}.let(midRepository::save).apply {
            this.myMember = myMember
            this.team = team
        }
    }
}

@Component
@Order(2)
class InitLoader2(
    private val teamRepository: TeamRepository,
    private val myMemberRepository: MyMemberRepository,
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        val team = teamRepository.findAll().first()!!
        val mymMember = myMemberRepository.findAll().first()!!

        team.mids
        mymMember.mids
    }
}

