kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":ketty-http-client-core"))
            }
        }
    }
}
