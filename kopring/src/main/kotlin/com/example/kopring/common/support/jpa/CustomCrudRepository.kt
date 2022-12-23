package com.example.kopring.common.support.jpa

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

inline fun <reified T, ID> CrudRepository<T, ID>.findByIdOrThrow(
    id: ID, e: Exception = IllegalArgumentException("${T::class.java.name} entity 를 찾을 수 없습니다. id=$id")
): T = findByIdOrNull(id) ?: throw e
