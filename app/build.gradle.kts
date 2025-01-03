plugins {
    id("java")
    id("application")
    id("checkstyle")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.freefair.lombok") version "8.6"
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.javalin:javalin:6.3.0")
    implementation("io.javalin:javalin-rendering:6.3.0")
    implementation("io.javalin:javalin-bundle:6.3.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.h2database:h2:2.3.230")
    implementation("gg.jte:jte:3.1.9")
}

tasks.test {
    useJUnitPlatform()
}