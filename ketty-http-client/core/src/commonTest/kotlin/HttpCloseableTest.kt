package io.ketty.http.client.core

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class HttpCloseableTest {
    @Test
    fun `instance equal`() = runTest {
        val counter = CloseCounter()
        counter.use {
            assertEquals(counter, it)
        }
    }

    /**
     * [HttpCloseable.use] invokes `action` only one time when it returns normally.
     */
    @Test
    fun `use invoke once on success`() = runTest {
        var count = 0
        Empty.use {
            count++
        }
        assertEquals(1, count)
    }

    /**
     * [HttpCloseable.use] invokes `action` only one time when it throws something.
     */
    @Test
    fun `use invoke once on failure`() = runTest {
        var count = 0
        assertFails {
            Empty.use {
                count++
                error("")
            }
        }
        assertEquals(1, count)
    }

    /**
     * [HttpCloseable.use] returns the returned value of `action`
     */
    @Test
    fun `use returns correctly`() = runTest {
        val text = "hello world".repeat(2)
        val actual = Empty.use {
            text
        }
        assertEquals(text, actual)
    }

    /**
     * [HttpCloseable.use] throws the thrown throwable of `action`
     */
    @Test
    fun `use throws correctly`() = runTest {
        val cause = Exception("hello, world!")
        val actual = assertFails {
            Empty.use {
                throw cause
            }
        }
        assertEquals(cause, actual)
    }

    /**
     * [HttpCloseable.use] invoke [HttpCloseable.close] only one time when `action` returns normally.
     */
    @Test
    fun `use close once on success`() = runTest {
        val counter = CloseCounter()
        counter.use { }
        assertEquals(1, counter.count)
    }

    /**
     * [HttpCloseable.use] invoke [HttpCloseable.close] only one time when `action` throw an exception.
     */
    @Test
    fun `use close once on failure`() = runTest {
        val counter = CloseCounter()
        assertFails {
            counter.use {
                error("")
            }
        }
        assertEquals(1, counter.count)
    }

    @Test
    fun `use close after action`() = runTest {
        val counter = CloseCounter()
        assertEquals(0, counter.count)
        counter.use {
            assertEquals(0, counter.count)
        }
        assertEquals(1, counter.count)
    }

    /**
     * Empty object that implements [HttpCloseable]
     */
    private data object Empty : HttpCloseable {
        override suspend fun close() = Unit
    }

    /**
     * Store invocation count of [close]
     */
    private data class CloseCounter(var count: Int = 0) : HttpCloseable {
        override suspend fun close() {
            count++
        }
    }
}
