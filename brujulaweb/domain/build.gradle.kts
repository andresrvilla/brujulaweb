plugins {
    id("java")
    id("io.freefair.lombok") version "8.4"
}

group = "net.brujulaweb.domain"
version = "1.0-SNAPSHOT"

dependencies {
}

tasks.test {
    useJUnitPlatform()
}