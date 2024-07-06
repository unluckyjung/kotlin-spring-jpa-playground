package com.example.kopring.mapstruct

interface EntityMapper<D, E> {
    fun dtoToEntity(dto: D): E

    fun entityToDto(entity: E): D
}
