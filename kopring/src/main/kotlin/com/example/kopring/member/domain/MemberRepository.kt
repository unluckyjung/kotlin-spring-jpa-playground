package com.example.kopring.member.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.jdbc.core.JdbcTemplate


fun MemberRepository.findByMemberId(id: Long): Member =
    findByIdOrNull(id) ?: throw NoSuchElementException("member id가 존재하지 않습니다. id: $id")

interface MemberRepository : JpaRepository<Member, Long>, CustomMemberRepository {
    fun findByName(name: String): Member?
    fun existsByName(name: String): Boolean
}

interface CustomMemberRepository {
    fun getMemberNameWithPrefix(member: Member): String
}

class CustomMemberRepositoryImpl(
    // JPA 에서 지원하지 않는 jdbcTemplate 를 사용한 로직. EX) BatchInsert
    private val jdbcTemplate: JdbcTemplate,
) : CustomMemberRepository {
    override fun getMemberNameWithPrefix(member: Member): String {
        return "prefix: ${member.name}"
    }
}
