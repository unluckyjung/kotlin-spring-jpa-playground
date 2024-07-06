package com.example.kopring.mapstruct

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface GoodallSameMapper : EntityMapper<GoodallDto, GoodallSameEntity> {
    companion object {
        val INSTANCE: GoodallSameMapper = Mappers.getMapper(GoodallSameMapper::class.java)
    }
}

class GoodallDto(
    val name: String,
    val age: Int,
)

class GoodallSameEntity(
    val name: String,
    val age: Int
)


@Mapper
interface GoodallDiffMapper : EntityMapper<GoodallDto, GoodallDiffEntity> {
    companion object {
        val INSTANCE: GoodallDiffMapper = Mappers.getMapper(GoodallDiffMapper::class.java)
    }

    @Mapping(source = "name", target = "nickName")
    override fun dtoToEntity(dto: GoodallDto): GoodallDiffEntity

    // @Mapping(source = "nickName", target = "name") 가능
    @InheritInverseConfiguration
    override fun entityToDto(entity: GoodallDiffEntity): GoodallDto
}

class GoodallDiffEntity(
    val nickName: String,
    val age: Int
)
