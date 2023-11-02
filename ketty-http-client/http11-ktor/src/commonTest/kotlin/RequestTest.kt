@file:Suppress("DEPRECATION") // Migrating to kotlinx-io

package io.ketty.http.client.http11.ktor

import io.ketty.http.client.core.HttpRequestData
import io.ktor.network.sockets.Connection
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.SocketAddress
import io.ktor.utils.io.ByteChannel
import io.ktor.utils.io.ReaderJob
import io.ktor.utils.io.WriterJob
import io.ktor.utils.io.close
import io.ktor.utils.io.core.readBytes
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.CoroutineContext
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class RequestTest {
    private val output = ByteChannel()
    private val emptyInput = ByteChannel()

    @BeforeTest
    fun closeInputBefore() {
        emptyInput.close()
    }

    @AfterTest
    fun `ensure input is empty`() {
        assertEquals(0, emptyInput.availableForRead, "input should not be written")
    }

    @Test
    fun `body-less request`() = runTest {
        val expected = """GET / HTTP/1.1
            |Host: testing
            |
            |
        """.trimMargin().replace("\r\n", "\n").replace("\n", "\r\n")

        val connection = KtorHttp11Connection(Connection(FailSocket, emptyInput, output))
        connection.request(HttpRequestData("GET", "/", mapOf("Host" to listOf("testing"))))
        output.close()

        assertEquals(expected, output.readRemaining().readBytes().decodeToString())
    }

    @Test
    fun `body request`() = runTest {
        val expected = """POST / HTTP/1.1
            |Host: testing
            |Content-Length: 4
            |
            |test
        """.trimMargin().replace("\r\n", "\n").replace("\n", "\r\n")

        val connection = KtorHttp11Connection(Connection(FailSocket, emptyInput, output))
        connection.request(HttpRequestData("POST", "/", mapOf("Host" to listOf("testing")), "test"))
        output.close()

        assertEquals(expected, output.readRemaining().readBytes().decodeToString())
    }

    private object FailSocket : Socket {
        override val localAddress: SocketAddress get() = fail()
        override val remoteAddress: SocketAddress get() = fail()
        override val coroutineContext: CoroutineContext get() = fail()
        override val socketContext: Job get() = fail()
        override fun attachForReading(channel: ByteChannel): WriterJob = fail()
        override fun attachForWriting(channel: ByteChannel): ReaderJob = fail()
        override fun close() = fail()
    }
}
