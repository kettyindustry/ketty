package io.ketty.module.test

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.Module
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ItemIntegrityModuleTest {
    /**
     * Verifies that [Module.name] and [Module.description] are both non-empty
     */
    @Test
    fun `description non-empty`() {
        val module = ItemIntegrityModule(FailItem, FailModule)
        assertTrue(module.name.isNotEmpty(), "ItemIntegrityModule name is empty")
        assertTrue(module.description.isNotEmpty(), "ItemIntegrityModule description is empty")
    }

    /**
     * The [Item] is verified even if the inner module succeeds
     */
    @Test
    fun `verifies valid with success`() = runTest {
        val expectedCheckCode = CheckCode.SAFE
        val item = UrlItem("a")
        val module = ItemIntegrityModule(item, SuccessModule(expectedCheckCode))
        val actualCheckCode = module.check(item)
        assertEquals(expectedCheckCode, actualCheckCode)
    }

    /**
     * The [Item] is verified even if the inner module failed
     */
    @Test
    fun `verifies valid with failure`() = runTest {
        val expectedThrowable = object : Throwable("Failed") {}
        val item = UrlItem("a")
        val module = ItemIntegrityModule(item, FailureModule(expectedThrowable))
        val actualFailure = assertFailsWith<Throwable> {
            module.check(item)
        }
        assertEquals(expectedThrowable, actualFailure)
    }

    /**
     * The [Item] is invalid even if the inner module succeeds
     */
    @Test
    fun `verifies invalid without calling module`() = runTest {
        val module = ItemIntegrityModule(UrlItem("a"), FailModule)
        val failure = assertFailsWith<AssertionError> {
            module.check(UrlItem("b"))
        }
        assertTrue("Item mismatch" in failure.message!!, "Invalid failure exception")
    }
}
