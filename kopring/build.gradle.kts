import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.11"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("kapt") version "1.9.25"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    // Spring Boot starters (versions managed by Spring Boot 3.3.11 BOM)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // JSON + Kotlin support
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // Database & Drivers
    // → let BOM manage mariadb version (3.3.4)
    implementation("org.mariadb.jdbc:mariadb-java-client")
    runtimeOnly("com.h2database:h2")

    // Utilities
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework:spring-aspects")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // QueryDSL (Jakarta)
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
    compileOnly("jakarta.annotation:jakarta.annotation-api")
    compileOnly("jakarta.persistence:jakarta.persistence-api")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest:kotest-property:5.9.1")
    testImplementation("io.mockk:mockk:1.14.2")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
}

// Kapt settings for QueryDSL
kapt {
    correctErrorTypes = true
    arguments {
        arg("querydsl.entityAccessors", "true")
    }
}

// Kotlin compile options
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

// Include generated Q-classes in sources
sourceSets {
    main {
        java {
            srcDir("build/generated/source/kapt/main")
        }
    }
}

kotlin {
    sourceSets.named("main") {
        kotlin.srcDir("build/generated/source/kapt/main")
    }
}

// Test setup
tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    jvmArgs(
        "--add-opens", "java.base/java.time=ALL-UNNAMED",
        "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED"
    )
}
