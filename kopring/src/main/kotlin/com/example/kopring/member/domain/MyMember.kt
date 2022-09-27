package com.example.kopring.member.domain

import javax.persistence.*

@Table(name = "my_member")
@Entity
class MyMember(
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null,

    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)