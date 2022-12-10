package com.example.kopring.file.controller

import com.example.kopring.file.service.FileService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("api/v1/file")
@RestController
class MultiPartController(
    private val fileService: FileService,
) {

    @PostMapping("/{id}")
    fun upload(
        @PathVariable id: Long,
        @RequestPart file: MultipartFile,
        @RequestPart dto: RequestDto,
        @RequestParam filename: String
    ) {
        fileService.upload(id, file, dto, filename)
    }

    // file-upload2 에서 따로따로 다 처리하는 방식
//    @PostMapping("/{id}/attribute")
//    fun upload2(
//        @PathVariable id: Long,
//        @RequestPart file: MultipartFile,
//        @RequestPart name: String,
//        @RequestPart age: String,
//        @RequestParam filename: String
//    ) {
//        println(id)
//    }

    // file-upload2 에서 보내는걸 한번에 받아서 처리하는 방식
    @PostMapping("/{id}/attribute")
    fun upload3(
        @PathVariable id: Long,
        @ModelAttribute dto: EntireDto,
        @RequestParam filename: String
    ) {
        fileService.upload(
            id = id,
            file = dto.file,
            dto = RequestDto(
                name = dto.name,
                age = dto.age,
            ),
            filename = filename,
        )
    }
}

data class RequestDto(
    val name: String,
    val age: Int,
)

data class EntireDto(
    val file: MultipartFile,
    val name: String,
    val age: Int,
)