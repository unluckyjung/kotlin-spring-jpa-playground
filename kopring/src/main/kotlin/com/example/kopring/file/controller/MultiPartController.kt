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
}

data class RequestDto(
    val name: String,
    val age: Int,
)
