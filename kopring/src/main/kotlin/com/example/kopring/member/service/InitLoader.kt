package com.example.kopring.member.service

import com.example.kopring.member.domain.MyMember
import com.example.kopring.member.domain.Team
import com.example.kopring.member.domain.TeamRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.data.repository.findByIdOrNull
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
        team!!.members.removeAt(1)
    }
}

@Order(3)
@Component
class InitLoader3(
    private val teamRepository: TeamRepository,
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {
        logger.info("lazyLoading Check lazy ======================")
        val team = teamRepository.findByIdOrNull(1) // team 만 select 하고 member join 쿼리 안나감.
        logger.info("lazyLoading Check: loading =====================")

        team?.let {
            // member join query 발생.
            logger.info("members size : ${it.members.size}")
        }
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}
