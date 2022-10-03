package com.example.kopring.member.domain

import javax.persistence.*

@Entity
class Team(
    @Column(name = "name")
    val name: String,

    members: MutableSet<AbstractMember>,

    @Column(name = "type")
    val type: Boolean = true,

    @Column(name = "team_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {

    @Embedded
    val members = Members(members)

    fun addMember(member: AbstractMember) {
        members.add(member)
    }
}
