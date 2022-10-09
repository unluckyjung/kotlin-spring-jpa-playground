package com.example.kopring.member.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import java.util.*

@Service
class FileService {
    fun upload(file: MultipartFile, filename: String) {
        logger.info("file: $file")
        logger.info("filename: $filename")

        if (file.isEmpty.not()) {
//            val filePath = Paths.get(BASIC_PATH + File.separator + file.originalFilename + UUID.randomUUID() + ".csv")
//            logger.info(filePath.toString())
//            file.transferTo(filePath.toFile())

            logger.info(Paths.get("").toAbsolutePath().toString())

            val filePath2 =
                System.getProperty("user.dir") + File.separator + BASIC_PATH + File.separator + file.originalFilename + UUID.randomUUID() + ".csv"
            file.transferTo(File(filePath2))
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private const val BASIC_PATH = "file-test"
        private const val TYPE_CSV = "text/csv"
    }
}
