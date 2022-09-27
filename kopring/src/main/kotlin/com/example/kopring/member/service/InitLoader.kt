package com.example.kopring.member.service

import com.example.kopring.member.domain.MyMember
import com.example.kopring.member.domain.MyMemberRepository
import com.example.kopring.member.domain.Team
import com.example.kopring.member.domain.TeamRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InitLoader(
    private val teamRepository: TeamRepository,
    private val myMemberRepository: MyMemberRepository,
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        Team("team1").apply {
            this.addMember(MyMember("member1"))
            this.addMember(MyMember("member2"))
        }.let(teamRepository::save)

        onlyTeamSave()
    }

    private fun onlyTeamSave() {
        val slaveTeam = Team("slaveTeam1")

        MyMember("ownerMember1").run {
            // member 쪽에서는 team 을 세팅하고 Member 는 저장되고 있지않음.
            this.team = slaveTeam
        }

        // slaveTeam만 저장되고, ownerMember1 는 저장되지 않음.
        teamRepository.save(slaveTeam)
    }
}
