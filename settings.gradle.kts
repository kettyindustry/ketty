rootProject.name = "ketty"

arrayOf("core", "mock").forEach { name ->
    include(":ketty-http-client-$name")
    project(":ketty-http-client-$name").projectDir = rootDir.resolve("ketty-http-client").resolve(name)
}

arrayOf("core", "mock").forEach { name ->
    include(":ketty-module-$name")
    project(":ketty-module-$name").projectDir = rootDir.resolve("ketty-module").resolve(name)
}

include(":ketty-core")
