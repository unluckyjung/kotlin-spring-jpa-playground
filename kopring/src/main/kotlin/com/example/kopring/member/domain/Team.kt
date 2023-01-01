package com.example.kopring.member.domain

import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@SQLDelete(sql = "UPDATE team SET deleted_at = NOW() WHERE team_id = ?")
@Where(clause = "deleted_at is null")
@Table(name = "team")
@Entity
class Team(
    @Column(name = "name")
    val name: String,

    // OneToMany 단방향
    @JoinColumn(name = "team_id")
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableList<MyMember> = mutableListOf(),

    @Column(name = "deleted_at")
    var deletedAt: ZonedDateTime? = null,

    @Column(name = "team_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    fun addMember(member: MyMember) {
        members.add(member)
    }

    fun addAllMember(members: List<MyMember>) {
        members.forEach {
            addMember(it)
        }
    }
}
