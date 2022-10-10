package com.example.kopring.member.controller

import com.example.kopring.member.service.FileService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("api/v1/file")
@RestController
class MultiPartController(
    private val fileService: FileService,
) {

    @PostMapping("/{id}")
    fun upload(@PathVariable id: Long, @RequestParam file: MultipartFile, @RequestParam filename: String) {
        fileService.upload(id, file, filename)
    }
}
