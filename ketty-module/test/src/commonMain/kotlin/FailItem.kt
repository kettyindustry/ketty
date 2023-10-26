package io.ketty.module.test

import io.ketty.module.core.Item
import kotlin.test.fail

/**
 * [Item.Http] that fails when used
 */
object FailItem : Item.Http() {
    override suspend fun url(): String = fail("Item should not have been used")
}
