rootProject.name = "ketty"

arrayOf("core", "test", "mock").forEach { name ->
    include(":ketty-module-$name")
    project(":ketty-module-$name").projectDir = rootDir.resolve("ketty-module").resolve(name)
}

include(":ketty-core")
