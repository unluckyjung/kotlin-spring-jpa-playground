package com.example.kopring.members.domain

import com.example.kopring.BaseEntity
import jakarta.persistence.*

@Entity(name = "abstract_member")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
abstract class AbstractMember(
    @Column(name = "name")
    val name: String,

    @Column(name = "id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity() {
    abstract fun isBlackList(): Boolean
}

@Entity
@DiscriminatorValue("BLACK")
class BMember(name: String) : AbstractMember(name = name) {
    override fun isBlackList(): Boolean {
        return true
    }
}

@Entity
@DiscriminatorValue("WHITE")
class WMember(name: String) : AbstractMember(name = name) {
    override fun isBlackList(): Boolean {
        return false
    }
}
