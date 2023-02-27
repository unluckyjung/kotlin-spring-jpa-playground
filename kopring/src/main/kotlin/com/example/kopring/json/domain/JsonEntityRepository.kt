package com.example.kopring.json.domain

import org.springframework.data.jpa.repository.JpaRepository

interface JsonEntityRepository : JpaRepository<JsonEntity, Long>
