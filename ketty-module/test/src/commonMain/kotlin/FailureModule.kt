package io.ketty.module.test

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.Module

data class FailureModule(private val cause: Throwable) : Module {
    override val name: String = "Failure"
    override val description: String = "Always throws a throwable."
    override suspend fun check(item: Item): CheckCode = throw cause
}
