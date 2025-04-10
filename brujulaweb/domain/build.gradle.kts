plugins {
    id("java")
    id("io.freefair.lombok") version "8.4"
}

group = "net.brujulaweb.domain"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("com.google.inject:guice:7.0.0")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.6")

    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.14.0")
}

tasks.test {
    useJUnitPlatform()
}