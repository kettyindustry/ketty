import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
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

    configure<KotlinMultiplatformExtension> {
        jvm()
        linuxX64()
        mingwX64()
    }
}
