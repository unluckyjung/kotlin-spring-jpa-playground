package com.example.kopring.member.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import java.util.UUID

@Service
class FileService {
    fun upload(file: MultipartFile, fileName: String) {
        logger.info("file: $file")
        logger.info("fileName: $fileName")

        if (file.isEmpty.not()) {
            if (file.contentType != TYPE_CSV) return

            val filePath = Paths.get(BASIC_PATH + File.separator + file.originalFilename + UUID.randomUUID() + ".csv")
            logger.info(filePath.toString())
//            file.transferTo(filePath.toFile())

//            val path = Paths.get(filePath).toAbsolutePath()
//            file.transferTo(path.toFile())
//            File(filePath).writeBytes(file.bytes)
//            logger.info(filePath)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
//        private const val BASIC_PATH =
        private const val TYPE_CSV = "text/csv"
    }
}
