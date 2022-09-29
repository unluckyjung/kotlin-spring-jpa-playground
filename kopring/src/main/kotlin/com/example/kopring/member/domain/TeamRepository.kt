package com.example.kopring.member.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TeamRepository : JpaRepository<Team, Long> {

    @Query("select t from Team t join fetch t.members")
    fun findAllFetch(): List<Team>

    @Query("select distinct t from Team t join fetch t.members")
    fun findAllFetchDistinct(): List<Team>
}
