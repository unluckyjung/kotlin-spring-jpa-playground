package com.example.kopring.member.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository : JpaRepository<Team, Long> {

//    @Query(
//        value = "select t " +
//            "from Team t join fetch t.members where t.id = ?1"
//    )
//    fun findByMyId(id: Long): Team
}
