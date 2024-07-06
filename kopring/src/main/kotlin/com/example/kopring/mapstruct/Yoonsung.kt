package com.example.kopring.mapstruct

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

data class YoonsungDto(
    val age: Int,
    val name: String?,
)

@Mapper
interface YoonsungV1Mapper : EntityMapper<YoonsungDto, YoonsungV1> {
    companion object {
        val INSTANCE: YoonsungV1Mapper = Mappers.getMapper(YoonsungV1Mapper::class.java)
    }
}

// 내부에서 값을 무시하는 경우 (name 필드 사용하지 않음)
data class YoonsungV1(
    val age: Int,
    val nickName: String = "goodall"
)


@Mapper
interface YoonsungV2Mapper : EntityMapper<YoonsungDto, YoonsungV2> {
    companion object {
        val INSTANCE: YoonsungV2Mapper = Mappers.getMapper(YoonsungV2Mapper::class.java)
    }

    //     error: Source and constant are both defined in @Mapping, either define a source or a constant.
    //     @org.mapstruct.Mapping(source = "name", target = "name", constant = "goodall")
    // source 와 constant 는 동시에 쓰일 수 없다.
    @Mapping(target = "name", constant = "goodall")
    override fun dtoToEntity(dto: YoonsungDto): YoonsungV2
}

data class YoonsungV2(
    val age: Int,
    // mapstruct 의 constant 와 디폴트값의 우선순위 차이 비교
    val name: String = "jys"
)

@Mapper
interface YoonsungV3Mapper : EntityMapper<YoonsungDto, YoonsungV3> {
    companion object {
        val INSTANCE: YoonsungV3Mapper = Mappers.getMapper(YoonsungV3Mapper::class.java)
    }

    @Mapping(target = "nickName", constant = "goodall")
    override fun dtoToEntity(dto: YoonsungDto): YoonsungV3
}

data class YoonsungV3(
    val age: Int,
    val name: String,
    val nickName: String,
)


