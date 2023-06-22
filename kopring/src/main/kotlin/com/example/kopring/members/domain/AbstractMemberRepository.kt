package com.example.kopring.members.domain

import org.springframework.data.jpa.repository.JpaRepository

interface AbstractMemberRepository : JpaRepository<AbstractMember, Long>

interface BMemberRepository : JpaRepository<BMember, Long>

interface WMemberRepository : JpaRepository<WMember, Long>
