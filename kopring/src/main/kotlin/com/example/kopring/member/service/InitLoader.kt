package com.example.kopring.member.service

import com.example.kopring.member.domain.Team
import com.example.kopring.member.domain.TeamRepository
import com.example.kopring.member.domain.WMember
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
        Team(
            "team3", type = false,
            // team.members.member = null 이 저장된다.
            // member5, 6 은 저장되지도 않는다? (저장되고 있음)
            members = mutableSetOf(
                WMember("member5")
            )
        ).let(teamRepository::save)
    }
}
