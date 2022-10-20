package com.example.kopring.member.service

import com.example.kopring.member.domain.MyMember
import com.example.kopring.member.domain.Team
import com.example.kopring.member.domain.TeamRepository
import com.example.kopring.member.domain.TestMember1
import com.example.kopring.member.domain.TestMember2
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
            this.addMember(TestMember1("member1"))
            this.addMember(TestMember1("member2"))
        }.let(teamRepository::save)

        Team("team2").apply {
            this.addMember(TestMember2("member3"))
            this.addMember(TestMember2("member4"))
        }.let(teamRepository::save)

        Team("team3").apply {
            this.addMember(TestMember1("member5"))
            this.addMember(TestMember1("member6"))
        }.let(teamRepository::save)
    }
}


//@Order(2)
//@Component
//class InitLoader2(
//    private val teamRepository: TeamRepository,
//) : CommandLineRunner {
//
//    @Transactional
//    override fun run(vararg args: String?) {
//        val team = teamRepository.findAll().firstOrNull()
//        team!!.members.removeAt(1)
//    }
//}

@Order(3)
@Component
class InitLoader3(
    private val teamRepository: TeamRepository,
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        val firstTeam = teamRepository.findAll().first()
        teamRepository.delete(firstTeam)
        teamRepository.deleteById(2L)
    }
}

//@Order(4)
//@Component
//class InitLoader4(
//    private val teamRepository: TeamRepository,
//) : CommandLineRunner {
//
//    @Transactional
//    override fun run(vararg args: String?) {
//        val team = teamRepository.findAll().lastOrNull()
//        team!!.delete()
//    }
//}
//
