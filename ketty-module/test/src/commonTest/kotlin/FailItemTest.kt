package io.ketty.module.test

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FailItemTest {
    @Test
    fun fails() = runTest {
        val failure = assertFailsWith<AssertionError> {
            FailItem.url()
        }
        assertTrue("Item should not have been used" in failure.message!!, "invalid failure message")
    }
}
