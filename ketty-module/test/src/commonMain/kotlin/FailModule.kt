package io.ketty.module.test

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.Module
import kotlin.test.fail

/**
 * Always calls [fail] in [check]
 */
object FailModule : Module {
    override val name: String = "Fail"
    override val description: String = "If the module is called then the test fails"
    override suspend fun check(item: Item): CheckCode = fail("Module should not have been called")
}
