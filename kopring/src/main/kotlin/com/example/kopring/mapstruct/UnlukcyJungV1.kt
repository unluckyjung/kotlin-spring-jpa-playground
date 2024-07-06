package com.example.kopring.mapstruct

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.mapstruct.factory.Mappers




data class Name(
    val value: String
)

class UnluckyJungDto(
    val age: Int,
    val name: String,
)

class UnlukcyJungV1(
    val age: Int,
    // String 으로 받음.
    name: String,
) {
    val name = Name(name)
}

@Mapper
interface UnlukcyJungV1Mapper : EntityMapper<UnluckyJungDto, UnlukcyJungV1> {
    companion object {
        val INSTANCE: UnlukcyJungV1Mapper = Mappers.getMapper(UnlukcyJungV1Mapper::class.java)
    }

    @Mapping(source = "name", target = "name")
    override fun dtoToEntity(dto: UnluckyJungDto): UnlukcyJungV1

    @Mapping(source = "name.value", target = "name")
    override fun entityToDto(entity: UnlukcyJungV1): UnluckyJungDto
}

class UnlukcyJungV2(
    val age: Int,
    // VO 로 받음.
    val name: Name,
)

@Mapper
interface UnlukcyJungV2Mapper : EntityMapper<UnluckyJungDto, UnlukcyJungV2> {
    companion object {
        val INSTANCE: UnlukcyJungV2Mapper = Mappers.getMapper(UnlukcyJungV2Mapper::class.java)

        @JvmStatic
        @Named("stringToName")
        fun stringToName(string: String): Name {
            return Name(string)
        }
    }

    @Mapping(source = "name", target = "name", qualifiedByName = ["stringToName"])
    override fun dtoToEntity(dto: UnluckyJungDto): UnlukcyJungV2

    @Mapping(source = "name.value", target = "name")
    override fun entityToDto(entity: UnlukcyJungV2): UnluckyJungDto
}
