kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.serialization.core)
                implementation(project(":ketty-module-core"))
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}