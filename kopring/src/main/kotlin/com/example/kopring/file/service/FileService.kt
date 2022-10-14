package com.example.kopring.file.service

import com.example.kopring.file.controller.RequestDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import java.util.*

@Service
class FileService {
    fun upload(id: Long, file: MultipartFile, dto: RequestDto, filename: String) {
        logger.info("file: $file")
        logger.info("id: $id")

        logger.info("name: ${dto.name}")
        logger.info("age: ${dto.age}")

        logger.info("filename: $filename")

        if (file.isEmpty.not()) {
            logger.info(Paths.get("").toAbsolutePath().toString())

            if (file.contentType == CSV_TYPE) {
                logger.info("this is csv_type")
            }

            fileSave(file)
        }
    }

    private fun fileSave(file: MultipartFile) {
        val filePath2 =
            System.getProperty("user.dir") + File.separator + BASIC_PATH + File.separator + file.originalFilename + UUID.randomUUID() + ".csv"
        file.transferTo(File(filePath2))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val BASIC_PATH = "file-test"
        private const val CSV_TYPE = "text/csv"
    }
}
