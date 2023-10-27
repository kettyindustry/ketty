package io.ketty.http.client.http11.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

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
        assertNull(parser.readHeader(""))
    }

    @Test
    fun `parse header`() {
        val (name, value) = parser.readHeader("Content-Type:application/json")!!
        assertEquals("Content-Type", name)
        assertEquals("application/json", value)
    }

    @Test
    fun `parse header with starting space`() {
        val (name, value) = parser.readHeader("Content-Type: application/json")!!
        assertEquals("Content-Type", name)
        assertEquals("application/json", value)
    }

    @Test
    fun `parse multi-line header with space`() {
        val (name, value) = parser.readHeader("Testing: Hello\r\n World\r\n Yes")!!
        assertEquals("Testing", name)
        assertEquals("Hello\nWorld\nYes", value)
    }

    @Test
    fun `parse multi-line header with tab`() {
        val (name, value) = parser.readHeader("Testing: Hello\r\n\tWorld\r\n\tYes")!!
        assertEquals("Testing", name)
        assertEquals("Hello\nWorld\nYes", value)
    }
}
