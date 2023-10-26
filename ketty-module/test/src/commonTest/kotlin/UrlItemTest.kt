package io.ketty.module.test

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UrlItemTest {
    @Test
    fun check() = runTest {
        val expectedUrl = "https://localhost"
        val item = UrlItem(expectedUrl)
        assertEquals(expectedUrl, item.url(), "invalid UrlItem url")
    }

    @Test
    fun `invalid url`() = runTest {
        val expectedUrl = "^invalid url"
        val item = UrlItem(expectedUrl)
        assertEquals(expectedUrl, item.url(), "invalid UrlItem url")
    }
}
