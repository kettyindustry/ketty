package io.ketty.http.client.http11.ktor

import io.ketty.http.client.core.ConnectException
import io.ketty.http.client.core.HttpClient
import io.ketty.http.client.core.HttpConnection
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.TypeOfService
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.connection
import io.ktor.network.tls.takeFrom
import io.ktor.network.tls.tls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

/**
 * `HTTP/1.1` implementation for [HttpClient] that follows [RFC 2068](https://www.rfc-editor.org/rfc/rfc2068)
 */
class KtorHttp11Client(
    private val readWriteTimeout: Long = 10_000, executionContext: CoroutineContext = EmptyCoroutineContext
) : HttpClient {
    private val selectorManager = SelectorManager(executionContext + Dispatchers.IO)

    override suspend fun connect(host: String, port: Int, tls: Boolean): HttpConnection = try {
        val socket = aSocket(selectorManager).tcp().connect(host, port) {
            keepAlive = false
            typeOfService = TypeOfService.IPTOS_RELIABILITY
            receiveBufferSize = 1024 / 2 // 0,5 kb
            socketTimeout = readWriteTimeout
        }

        val connection = if (tls) {
            socket.tls(coroutineContext = coroutineContext) {
                takeFrom(platformTlsConfig)
            }.connection()
        } else {
            socket.connection()
        }

        KtorHttp11Connection(connection)
    } catch (exception: Throwable) {
        throw ConnectException(exception)
    }

    override suspend fun close() {
        withContext(Dispatchers.IO) {
            selectorManager.close()
        }
    }
}
