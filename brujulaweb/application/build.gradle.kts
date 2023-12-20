plugins {
    id("java")
    id("application")
}

group = "net.brujulaweb.application"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":domain"))

    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

    //WEB
    implementation("io.javalin:javalin:5.6.3")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

    // DATABASE
    implementation("commons-dbutils:commons-dbutils:1.8.1")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.zaxxer:HikariCP:5.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.password4j:password4j:1.7.3")
    implementation("com.google.inject:guice:7.0.0")

}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "net.brujulaweb.server.BrujulaWebServer"
}

tasks.register("prepareKotlinBuildScriptModel"){}