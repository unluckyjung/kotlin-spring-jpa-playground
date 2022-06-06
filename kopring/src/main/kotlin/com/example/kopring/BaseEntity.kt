package com.example.kopring

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
abstract class BaseEntity(
    @Column(name = "created_at")
    var createdAt: ZonedDateTime = DEFAULT_TIME,

    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime = DEFAULT_TIME
) {
    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now()
        updatedAt = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = ZonedDateTime.now()
    }

    companion object {
        val DEFAULT_TIME = ZonedDateTime.of(LocalDateTime.MAX, ZoneId.of("Asia/Seoul"))
    }
}
