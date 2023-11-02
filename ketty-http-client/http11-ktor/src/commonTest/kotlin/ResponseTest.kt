@file:Suppress("DEPRECATION") // Migrating to kotlinx-io

package io.ketty.http.client.http11.ktor

import io.ktor.utils.io.ByteChannel
import io.ktor.utils.io.close
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ResponseTest {
    private val input = ByteChannel()

    @AfterTest
    fun `ensure input is empty`() {
        assertEquals(0, input.availableForRead, "input has been read")
    }

    @Test
    fun `body-less response`() = runTest {
        input.writeStringUtf8(
            """HTTP/1.1 204 No Content
            |Server: cloudflare
            |Content-Length: 0
            |
            |
            """.trimMargin().replace("\r\n", "\n").replace("\n", "\r\n")
        )
        input.close()

        val response = KtorHttp11Response(input)

        val (code, reason) = response.status()
        assertEquals(204, code)
        assertEquals("No Content", reason)

        val headers = response.headers()
        assertContentEquals(listOf("Server" to "cloudflare", "Content-Length" to "0"), headers)

        val body = response.body()
        assertEquals(0, body.availableForRead)
    }

    @Test
    fun `body response`() = runTest {
        input.writeStringUtf8(
            """HTTP/1.1 200 OK
            |Server: cloudflare
            |Content-Length: 6
            |
            |server
            """.trimMargin().replace("\r\n", "\n").replace("\n", "\r\n")
        )
        input.close()

        val response = KtorHttp11Response(input)

        val (code, reason) = response.status()
        assertEquals(200, code)
        assertEquals("OK", reason)

        val headers = response.headers()
        assertContentEquals(listOf("Server" to "cloudflare", "Content-Length" to "6"), headers)

        val body = response.body()
        val bodyText = body.readRemaining(6).readText()
        assertEquals("server", bodyText)
    }
}
