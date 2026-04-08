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
    version.set("2.33.0")
}

val allureVersion = "2.33.0"
val jacksonVersion = "2.15.2"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.codeborne:selenide:7.14.0")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("org.aeonbits.owner:owner:1.0.12")
    implementation("io.rest-assured:rest-assured:6.0.0")

    // Jackson — убрали дубликаты, версия вынесена в переменную
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")

    // БД — убрали дубликаты
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("commons-dbutils:commons-dbutils:1.8.1")

    implementation("com.opencsv:opencsv:5.9")

    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")

    implementation("io.github.bonigarcia:webdrivermanager:6.3.4")
    implementation("io.qameta.allure:allure-selenide:$allureVersion")

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}

tasks.test {
    useJUnitPlatform()
}

// Вынесли общую конфигурацию в функцию — DRY!
fun Test.configureTask(tags: String, headless: Boolean = false) {
    useJUnitPlatform {
        includeTags(tags)
    }
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    outputs.upToDateWhen { false }
    if (headless) {
        systemProperty("chromeoptions.args", "--headless=new")
    }
}

// Теперь каждая таска — одна строка вместо 6
tasks.register<Test>("apiAndUiTest") { configureTask("API | UI", headless = true) }
tasks.register<Test>("smokeTest")    { configureTask("SMOKE", headless = true) }
tasks.register<Test>("regressionTest") { configureTask("REGRESSION", headless = true) }
tasks.register<Test>("apiTest")      { configureTask("API") }
tasks.register<Test>("uiTest")       { configureTask("UI") }

// Для тасок с несколькими тегами — небольшое расширение
tasks.register<Test>("apiSmokeTest") {
    useJUnitPlatform {
        includeTags("SMOKE", "API")
        excludeTags("REGRESSION")
    }
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    outputs.upToDateWhen { false }
}

tasks.register<Test>("uiSmokeTest") {
    useJUnitPlatform {
        includeTags("SMOKE", "UI")
        excludeTags("REGRESSION")
    }
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    outputs.upToDateWhen { false }
}

tasks.register<Test>("apiRegressionTest") {
    useJUnitPlatform {
        includeTags("API", "REGRESSION")
        excludeTags("SMOKE")
    }
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    outputs.upToDateWhen { false }
}

tasks.register<Test>("uiRegressionTest") {
    useJUnitPlatform {
        includeTags("UI", "REGRESSION")
        excludeTags("SMOKE")
    }
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    outputs.upToDateWhen { false }
}