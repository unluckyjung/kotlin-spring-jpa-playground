package com.example.kopring.member.domain

import com.example.kopring.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Member(

    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,

    createdAt: LocalDateTime
) : BaseEntity(createdAt = createdAt) {
    constructor(
        name: String,
        id: Long = 0L,
        createdAt: LocalDateTime = LocalDateTime.now()
    ) : this(id, name, createdAt)
}
