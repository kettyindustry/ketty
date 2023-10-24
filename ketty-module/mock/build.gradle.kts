kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":ketty-module-core"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}