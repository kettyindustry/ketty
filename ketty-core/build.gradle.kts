kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":ketty-module-core"))
                implementation(project(":ketty-http-client-core"))
            }
        }
    }
}
