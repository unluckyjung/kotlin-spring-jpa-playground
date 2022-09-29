package com.example.kopring.member.service

import com.example.kopring.member.domain.MyMember
import com.example.kopring.member.domain.Team
import com.example.kopring.member.domain.TeamRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.data.repository.findByIdOrNull
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
            this.addMember(MyMember("member3"))
        }.let(teamRepository::save)

        Team("team2").apply {
            this.addMember(MyMember("member4"))
            this.addMember(MyMember("member5"))
            this.addMember(MyMember("member6"))
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
        val team = teamRepository.findByIdOrNull(1)
        logger.info("================")
        val members = team!!.members    // lazy 로 조회 안함.
        logger.info("================")

        // 이때 쿼리 한번나감
        members.forEachIndexed { index, myMember ->
            logger.info("index: $index")
            logger.info("myMember name: ${myMember.name}")
        }

        val teams = teamRepository.findAll()
        for (team in teams) {
            // 1 + N 문제 발생, team 내의 proxy members 를 호출하기위해 매번 쿼리발생
            logger.info("member size ${team.members.size}")
        }
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}

@Order(3)
@Component
class InitLoader3(
    private val teamRepository: TeamRepository,
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {

        // N+1 은 발생하지 않으나 총 2 x 3 = 6개가 조회 되어버린다.
        val teams1 = teamRepository.findAllFetch()
        logger.info("teams1 size ${teams1.size}")   // 6

        // member 의 pk 가 다르기때문에 조인 단계에서 다른것으로 판단하기 때문
        for (team in teams1) {
            // team_pk = 1, member_pk = 1, member_team_id = 1
            // team_pk = 1, member_pk = 2, member_team_id = 1
            // team_pk = 1, member_pk = 3, member_team_id = 1
            logger.info("fetch team, member size ${team.members.size}") // 3
        }


        val teams2 = teamRepository.findAllFetchDistinct()
        logger.info("teams2 size ${teams1.size}")   // 2
        for (team in teams2) {
            // 중복도 제거 된다.
            logger.info("distinct fetch team, member size ${team.members.size}")    // 3
        }
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}
