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

    fun delete() {
        // cascade.REMOVE 옵션이 안먹는다.
        val now = ZonedDateTime.now()
        deletedAt = now

        // 직접 값을 넣어주어야 한다.
        // CascadeType.PERSIST 때문에 값 변경이 적용 된다. member 가 직접 변경되는것이라 적용되어짐.
        members.forEach {
            it.deletedAt = now
        }
    }
}