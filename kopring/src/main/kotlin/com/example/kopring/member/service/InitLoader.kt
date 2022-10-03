package com.example.kopring.member.service

import com.example.kopring.member.domain.MyMember
import com.example.kopring.member.domain.MyMemberRepository
import com.example.kopring.member.domain.Team
import com.example.kopring.member.domain.TeamRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

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

@Order(3)
@Component
class InitLoader3(
    private val teamRepository: TeamRepository,
    private val memberRepository: MyMemberRepository,
    private val entityManager: EntityManager,
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {
        entityManager.clear()

        logger.info("lazyLoading team Check lazy ======================")
        val team = teamRepository.findByIdOrNull(1) // team 만 select 하고 member join 쿼리 안나감.
        logger.info("lazyLoading team Check: loading =====================")

        // OneToMany 방향에선 open 없이도 lazyLoading 이 그냥 된다..?
        team?.let {
            // member join query 발생.
            logger.info("members size : ${it.members.size}")
        }

        entityManager.clear()

        // ManyToOne 방향에선 lazy 를 위해 open 이 필요하다..?
        logger.info("lazyLoading Check lazy ======================")
        val member = memberRepository.findByIdOrNull(1) // lazy 안되고 team 까지 전부 조회됌
        logger.info("lazyLoading Check: loading =====================")
        logger.info("team name : ${member!!.team!!.name}")
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}
