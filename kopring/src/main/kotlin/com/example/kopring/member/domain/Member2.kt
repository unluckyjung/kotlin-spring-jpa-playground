package com.example.kopring.member.domain

import com.example.kopring.BaseEntity
import org.springframework.data.domain.Persistable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

/*
// uuid를 직접 넣어주는 경우.
@Entity
class Member2(

    @Column(name = "member2_uuid")
    @Id
    val uuid: String = ""
) : BaseEntity()
*/

// Persistable 구현 후 isNew()를 이용해 merge pass, 이로인해 저장시 select 쿼리 나가는것 방지
@Entity
class Member2(

    @Column(name = "member2_uuid")
    @Id
    val uuid: String = ""
) : Persistable<String>, BaseEntity() {

    override fun getId(): String {
        return uuid
    }

    override fun isNew(): Boolean {
        return super.createdAt == DEFAULT_TIME
    }
}

/*
// Generator 로 uuid 만들기 (DI시 타입 주의 필요)
@Entity
class Member2(

    @Column(name = "member2_uuid")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    val uuid: String = ""
) : Persistable<String>, BaseEntity() {

    override fun getId(): String {
        return uuid
    }

    override fun isNew(): Boolean {
        return super.createdAt == DEFAULT_TIME
    }
}
*/
