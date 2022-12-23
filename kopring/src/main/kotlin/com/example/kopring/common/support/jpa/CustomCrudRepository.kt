package com.example.kopring.common.support.jpa

import org.springframework.data.repository.CrudRepository


inline fun <reified T, ID : Any> CrudRepository<T, ID>.findByIdOrThrow(id: ID, e: Exception? = null): T =
    findById(id).orElseThrow { e ?: IllegalArgumentException("${T::class.java.name} entity 를 찾을 수 없습니다. id=$id") }

