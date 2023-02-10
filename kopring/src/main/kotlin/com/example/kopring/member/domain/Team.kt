package com.example.kopring.member.domain

import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@SQLDelete(sql = "UPDATE team SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is null")
@Table(name = "team")
@Entity
class Team(
    @Column(name = "name", nullable = false)
    val name: String,

    // 연관관계 주인은 MyMember(fk team_id 소유)
    @OneToMany(mappedBy = "team", cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableList<MyMember> = mutableListOf(),

    deletedAt: ZonedDateTime? = null,

    @Column(name = "id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    @Column(name = "deleted_at", nullable = true)
    var deletedAt: ZonedDateTime? = deletedAt
        protected set

    fun addMember(member: MyMember) {
        members.add(member)
        member.team = this
    }

    fun addAllMembers(members: List<MyMember>) {
        members.forEach {
            addMember(it)
        }
    }

    fun delete() {
        this.deletedAt = ZonedDateTime.now()
        members.forEach {
            it.delete()
        }
    }
}
