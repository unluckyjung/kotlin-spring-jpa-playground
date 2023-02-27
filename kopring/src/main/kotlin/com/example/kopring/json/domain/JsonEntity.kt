package com.example.kopring.json.domain

import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@TypeDef(name = "json", typeClass = JsonStringType::class)
@Entity
class JsonEntity(
    @Type(type = "json")
    @Column(name = "json_body", nullable = false, columnDefinition = "json")
    val jsonBody: Map<*, *>,

    @Column(name = "id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
