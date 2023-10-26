package io.ketty.module.mock

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.ItemUnsupportedException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MockModuleTest {
    /**
     * The provided item and returned check code should not altered
     */
    @Test
    fun integrity() = runTest {
        val item: Item = Url("testing")
        val module = MockModule {
            assertEquals(item, it, "item mismatch")
            CheckCode.SAFE
        }
        val checkCode = module.check(item)
        assertEquals(CheckCode.SAFE, checkCode, "checkCode mismatch")
    }

    /**
     * The module action should be called only once
     */
    @Test
    fun once() = runTest {
        var called = false
        val module = MockModule {
            assertFalse(called, "module called twice")
            called = true
            CheckCode.SAFE
        }
        assertFalse(called, "module called during initialization")
        module.check(Url("testing"))
        assertTrue(called, "module not called")
    }

    /**
     * The module action should be called only once, even when an exception is thrown
     */
    @Test
    fun `once with error`() = runTest {
        var called = false
        val module = MockModule {
            assertFalse(called, "module called twice")
            called = true
            throw ItemUnsupportedException("x")
        }
        assertFalse(called, "module called during initialization")
        assertFailsWith<ItemUnsupportedException> {
            module.check(Url("testing"))
        }
        assertTrue(called, "module not called")
    }

    private data class Url(val url: String) : Item.Http() {
        override suspend fun url(): String = url
    }
}