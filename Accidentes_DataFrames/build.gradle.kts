import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    // Para generar modelos de DataFrames
    id("org.jetbrains.kotlinx.dataframe") version "0.8.1"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // DataFrames de Kotlin Jetbrains
    implementation("org.jetbrains.kotlinx:dataframe:0.8.1")
    // LetsPlot
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin:4.0.0")
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:2.4.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

// Data Schema generator
// Make IDE aware of the generated code:
kotlin.sourceSets.getByName("main").kotlin.srcDir("build/generated/ksp/main/kotlin/")