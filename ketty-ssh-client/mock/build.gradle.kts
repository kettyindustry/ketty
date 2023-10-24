kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":ketty-ssh-client-core"))
            }
        }
    }
}
