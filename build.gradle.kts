plugins {
    id("java")
    id("io.qameta.allure") version "2.11.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allure {
    version.set("2.24.0")
}

val allureVersion = "2.25.0"
val ownerVersion = "1.0.9"
val jacksonVersion = "2.17.0"
val assertjVersion = "3.22.0"
val lombokVersion = "1.18.30"
val slf4jVersion = "2.0.16"
val selenideVersion = "7.8.1"

dependencies {
    implementation("org.projectlombok:lombok:$lombokVersion")
    implementation("io.github.bonigarcia:webdrivermanager:5.9.2")
    testImplementation("io.qameta.allure:allure-selenide:$allureVersion")
    implementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Source: https://mvnrepository.com/artifact/com.codeborne/selenide
    implementation("com.codeborne:selenide:7.14.0")
    implementation("org.aspectj:aspectjtools:1.9.22")
    implementation("org.aspectj:aspectjweaver:1.9.22")
    implementation("com.codeborne:selenide:$selenideVersion")
    implementation("io.qameta.allure:allure-junit5:$allureVersion")
    implementation("net.datafaker:datafaker:2.2.2")
    implementation("org.aeonbits.owner:owner:$ownerVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    // Добавь эту строку:
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.25.3")
    // Source: https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    implementation("io.rest-assured:rest-assured:6.0.0")
    // Source: https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
    implementation("org.apache.logging.log4j:log4j-core:2.25.3")
    implementation("org.apache.httpcomponents.core5:httpcore5:5.2.1")
    // Source: https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.21.2")
    // Source: https://mvnrepository.com/artifact/org.assertj/assertj-core
    testImplementation("org.assertj:assertj-core:3.27.7")
    implementation("com.opencsv:opencsv:5.9")
    // Source: https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.2")
    // Source: https://mvnrepository.com/artifact/commons-dbutils/commons-dbutils
    implementation("commons-dbutils:commons-dbutils:1.8.1")

    implementation("io.qameta.allure:allure-java-commons:2.24.0")
    testImplementation("io.qameta.allure:allure-junit5:2.24.0")
}

tasks.test {
    useJUnitPlatform()
    outputs.upToDateWhen { false }
    systemProperty("allure.results.directory", "build/allure-results")
    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
    systemProperty("selenide.headless", "true")
}


tasks.register<Test>("smokeTest") {
            description = "Runs smoke tests"
            group = "verification"
            useJUnitPlatform {
                includeTags("SMOKE")
            }
        }

tasks.register<Test>("regressionTest") {
    description = "Runs regression tests"
    group = "verification"
    useJUnitPlatform {
        includeTags("REGRESSION")
    }
}

tasks.register<Test>("e2eTest") {
    description = "Runs end-to-end tests"
    group = "verification"
    useJUnitPlatform {
        includeTags("E2E")
    }
}

tasks.register<Test>("uiTest") {
    description = "Runs UI tests"
    group = "verification"
    useJUnitPlatform {
        includeTags("UI")
    }
}

tasks.register<Test>("apiTest") {
    description = "Runs API tests"
    group = "verification"
    useJUnitPlatform {
        includeTags("API")
    }
}

tasks.register<Test>("dbTest") {
    description = "Runs DB tests"
    group = "verification"
    useJUnitPlatform {
        includeTags("DB")
    }
}

tasks.register<Test>("unitTest") {
    description = "Runs unit tests"
    group = "verification"
    useJUnitPlatform {
        includeTags("UNIT")
    }
}
