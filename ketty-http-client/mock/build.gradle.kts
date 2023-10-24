kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":ketty-http-client-core"))
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
