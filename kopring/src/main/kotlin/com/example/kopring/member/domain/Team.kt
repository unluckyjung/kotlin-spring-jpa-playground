package com.example.kopring.member.domain

import javax.persistence.*

@Entity
class Team(
    @Column(name = "name")
    val name: String,

    // 연관관계 주인은 MyMember(fk team_id 소유)
    @JoinColumn(name = "team_id") // OneToMany 단방향
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableList<AbstractMember> = mutableListOf(),

    @Column(name = "type")
    val type: Boolean = true,

    @Column(name = "team_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    fun addMember(member: AbstractMember) {
        members.add(member)
//        member.team = this
    }
}
