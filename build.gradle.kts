plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.freefair.lombok") version "5.3.0"
    kotlin("plugin.noarg") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"
    kotlin("plugin.lombok") version "1.9.22"
    kotlin("kapt") version "1.9.22"
}

val mysqlVersion = "8.0.33"

group = "kr.goldenmine"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}


allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

kapt {
    keepJavacAnnotationProcessors = true
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(group="org.springframework.boot", name="spring-boot-starter-data-jpa")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
    implementation(group="com.squareup.retrofit2", name="converter-gson", version="2.11.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.11.0")
    implementation("com.squareup.retrofit2:converter-jaxb:2.11.0")

    implementation(group="mysql", name="mysql-connector-java", version=mysqlVersion)

    annotationProcessor("org.projectlombok:lombok:1.18.24")
    compileOnly("org.projectlombok:lombok:1.18.24")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
