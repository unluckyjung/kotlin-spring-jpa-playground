package com.example.kopring.member.controller

import com.example.kopring.member.service.FileService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RequestMapping("api/v1/file")
@RestController
class MultiPartController(
    private val fileService: FileService,
) {

    @PostMapping("/{filename}")
    fun upload(@RequestParam file: MultipartFile, @PathVariable filename: String) {
        fileService.upload(file, filename)
    }
}
