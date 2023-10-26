package io.ketty.module.test

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FailModuleTest {
    private val module = FailModule

    @Test
    fun `description non-empty`() {
        assertTrue(module.name.isNotEmpty(), "FailModule name is empty")
        assertTrue(module.description.isNotEmpty(), "FailModule description is empty")
    }

    @Test
    fun check() = runTest {
        val failure = assertFailsWith<AssertionError> {
            module.check(UrlItem(""))
        }
        assertTrue("Module should not have been called" in failure.message!!, "Invalid failure message")
    }
}
