package com.example.kopring.member.domain

import org.springframework.data.jpa.repository.JpaRepository

interface Member2Repository : JpaRepository<Member2, Long> {
    fun findByName(name: String): Member2ProjectionData?
}

interface Member2ProjectionData {
    val id: Long
    val nickName: String
}

interface Dto2

data class Dto1(
    val name: String,
    val dto2: Dto2,
)


