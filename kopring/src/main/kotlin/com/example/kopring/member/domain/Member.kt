package com.example.kopring.member.domain

import com.example.kopring.BaseEntity
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
class Member(

    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,

    createdAt: ZonedDateTime
) : BaseEntity(createdAt = createdAt) {
    constructor(
        name: String,
        id: Long = 0L,
        createdAt: ZonedDateTime = ZonedDateTime.now()
    ) : this(id, name, createdAt)
}
