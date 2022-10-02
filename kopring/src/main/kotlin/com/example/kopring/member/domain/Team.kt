package com.example.kopring.member.domain

import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE team SET deleted_at = NOW() WHERE team_id = ?")
@Table(name = "team")
@Entity
class Team(
    @Column(name = "name")
    val name: String,

    // 연관관계 주인은 MyMember(fk team_id 소유)
    @OneToMany(mappedBy = "team", cascade = [CascadeType.ALL], orphanRemoval = true)
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
        member.team = this
    }
}