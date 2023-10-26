package io.ketty.module.test

import io.ketty.module.core.Module
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FailureModuleTest {
    private val module = FailureModule(TestThrowable)

    /**
     * Verifies that [Module.name] and [Module.description] are both non-empty
     */
    @Test
    fun `description non-empty`() {
        assertTrue(module.name.isNotEmpty(), "FailureModule name is empty")
        assertTrue(module.description.isNotEmpty(), "FailureModule description is empty")
    }

    @Test
    fun fails() = runTest {
        assertFailsWith<TestThrowable> {
            module.check(FailItem)
        }
    }

    private data object TestThrowable : Throwable("Throwable used in FailureModule tests")
}
