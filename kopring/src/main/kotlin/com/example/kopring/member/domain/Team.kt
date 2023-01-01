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

    members: MutableList<MyMember> = mutableListOf(),

    @Column(name = "team_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    @Column(name = "deleted_at")
    var deletedAt: ZonedDateTime? = null

    @Embedded
    val members: MyMembers = MyMembers(members)

    fun addMember(member: MyMember) {
        members.addMember(member)
    }

    fun addAllMember(members: List<MyMember>) {
        members.forEach {
            addMember(it)
        }
    }

    fun delete() {
        val now = ZonedDateTime.now()
        deletedAt = now
        members.members.forEach {
            it.deletedAt = now
        }
    }
}
