package com.example.kopring.member.domain

import javax.persistence.*

@Table(name = "member2")
@Entity
class Member2(
    @Column(name = "name")
    val name: String,

    @Column(name = "nick_name")
    val nickName: String,

    @Column(name = "id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
