package com.example.kopring.member.domain

import javax.persistence.*

@Entity
class Mid(
    @Column(name = "name")
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myMember")
    var myMember: MyMember? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team")
    var team: Team? = null,


    @Column(name = "mid_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
