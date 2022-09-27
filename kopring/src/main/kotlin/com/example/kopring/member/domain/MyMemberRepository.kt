package com.example.kopring.member.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MyMemberRepository : JpaRepository<MyMember, Long>