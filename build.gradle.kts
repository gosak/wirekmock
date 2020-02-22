import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL

val projectVersion: String by project.extra
val kotlinVersion: String by project.extra
val wiremockVersion: String by project.extra
val kluentVersion: String by project.extra
val junitVersion: String by project.extra
val jupiterVersion: String by project.extra
val gradleVersion: String by project.extra

buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
    }
}

plugins {
    kotlin("jvm") version "1.3.61"
    id("io.gitlab.arturbosch.detekt") version "1.1.1"
}

apply(plugin = "io.gitlab.arturbosch.detekt")


repositories {
    maven("https://dl.bintray.com/kotlin/kotlinx/")
    jcenter()
}

dependencies {
    api(group = "com.github.tomakehurst", name = "wiremock-jre8", version = wiremockVersion)

    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect")

    testImplementation(group = "org.amshove.kluent", name = "kluent", version = kluentVersion)
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = junitVersion)
    testCompile(group = "org.junit.jupiter", name = "junit-jupiter-api", version = jupiterVersion)
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = jupiterVersion)
}

tasks.withType<KotlinCompile<KotlinJvmOptions>> {
    group = "pl.gosak.wirekmock"
    version = projectVersion
    kotlinOptions {
        jvmTarget = "$VERSION_11"
        freeCompilerArgs = listOf("-Xuse-experimental=kotlin.Experimental")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = FULL
    }
}

tasks.withType<Wrapper> {
    gradleVersion = gradleVersion
}
