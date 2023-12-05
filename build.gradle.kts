plugins {
    kotlin("jvm") version "1.9.20"
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "xyz.starsdust"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")

    // 串口通信
    implementation("com.fazecast:jSerialComm:2.10.4")

    // 数据库
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
    implementation("org.mybatis:mybatis:3.5.14")

    // 日志
    implementation("org.slf4j:slf4j-api:2.0.9")
    testImplementation("org.slf4j:slf4j-simple:2.0.9")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        resources {
            srcDir("src/main/kotlin")
            include("**/*.properties")
            include("**/*.xml")
            include("**/*.fxml")
        }
    }
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
    setPlatform("win")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("xyz.starchen.wincc.Application")
}

tasks.withType<JavaCompile> {
    options.encoding = "utf-8"
}