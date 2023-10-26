package io.ketty.module.test

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.Module

data class SuccessModule(private val checkCode: CheckCode) : Module {
    override val name: String = "Success"
    override val description: String = "Always returns the same check code"
    override suspend fun check(item: Item): CheckCode = checkCode
}
