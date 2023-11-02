package io.ketty.http.client.http11.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Http11ResponseParserTest {
    private val parser = Http11ResponseParser()

    @Test
    fun `parse Status-Line`() {
        val (code, reason) = parser.readStatusLine("HTTP/1.1 400 Bad Request")
        assertEquals(400, code)
        assertEquals("Bad Request", reason)
    }

    @Test
    fun `parse another Status-Line`() {
        val (code, reason) = parser.readStatusLine("HTTP/1.1 200 OK")
        assertEquals(200, code)
        assertEquals("OK", reason)
    }

    @Test
    fun `parse header but empty line`() {
        val headers = parser.readHeaders(emptyList())
        assertTrue(headers.isEmpty())
    }

    @Test
    fun `parse header`() {
        val (name, value) = parser.readHeaders(listOf("Content-Type:application/json")).single()
        assertEquals("Content-Type", name)
        assertEquals("application/json", value)
    }

    @Test
    fun `parse header with starting space`() {
        val (name, value) = parser.readHeaders(listOf("Content-Type: application/json")).single()
        assertEquals("Content-Type", name)
        assertEquals("application/json", value)
    }

    @Test
    fun `parse multi-line header with space`() {
        val (name, value) = parser.readHeaders(listOf("Testing: Hello", " World", " Yes")).single()
        assertEquals("Testing", name)
        assertEquals("Hello\nWorld\nYes", value)
    }

    @Test
    fun `parse multi-line header with horizontal tab`() {
        val (name, value) = parser.readHeaders(listOf("Testing: Hello", "\tWorld", "\tYes")).single()
        assertEquals("Testing", name)
        assertEquals("Hello\nWorld\nYes", value)
    }
}
