import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
}

allprojects {
    group = "io.ketty"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin-multiplatform")
    apply(plugin = "kotlinx-serialization")

    configure<KotlinMultiplatformExtension> {
        jvm()
        linuxX64()
    }
}
