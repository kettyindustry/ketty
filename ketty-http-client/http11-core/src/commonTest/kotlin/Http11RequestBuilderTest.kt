package io.ketty.http.client.http11.core

import io.ktor.utils.io.core.readBytes
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Http11RequestBuilderTest {
    private val builder = Http11RequestBuilder()

    private fun buildAssertEquals(text: String) {
        val packet = builder.build()
        val actualText = packet.readBytes().decodeToString()
        assertEquals(text, actualText)
    }

    @AfterTest
    fun release() {
        builder.release()
    }

    @Test
    fun `valid request-line`() {
        builder.writeRequestLine(Http11Method.Get, "/")
        buildAssertEquals("GET / HTTP/1.1\r\n")
    }

    @Test
    fun `valid single header`() {
        builder.writeHeader("Testing", "Value")
        buildAssertEquals("Testing: Value\r\n")
    }

    @Test
    fun `valid many headers`() {
        repeat(10) {
            builder.writeHeader("Testing", "Value")
        }
        buildAssertEquals("Testing: Value\r\n".repeat(10))
    }

    @Test
    fun `new line`() {
        builder.writeNewLine()
        buildAssertEquals("\r\n")
    }
}
