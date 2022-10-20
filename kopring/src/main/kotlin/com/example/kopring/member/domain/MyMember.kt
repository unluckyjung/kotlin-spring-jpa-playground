package com.example.kopring.member.domain

import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "data_type")
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE my_member SET deleted_at = NOW() WHERE member_id = ?")
@Table(name = "my_member")
@Entity
sealed class MyMember(
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null,

    @Column(name = "deleted_at")
    var deletedAt: ZonedDateTime? = null,

    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)

@SQLDelete(sql = "UPDATE my_member SET deleted_at = NOW() WHERE member_id = ?")
@Entity
@DiscriminatorValue("1")
class TestMember1(
    name: String,
) : MyMember(
    name = name
)

@SQLDelete(sql = "UPDATE my_member SET deleted_at = NOW() WHERE member_id = ?")
@Entity
@DiscriminatorValue("2")
class TestMember2(
    name: String,
) : MyMember(
    name = name
)
