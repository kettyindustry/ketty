package io.ketty.http.client.core

import kotlin.coroutines.cancellation.CancellationException

/**
 * Low-level http client
 */
interface HttpClient : HttpCloseable {
    /**
     * Connects to [host]:[port].
     * If [tls] is enabled then a TLS connection is made.
     *
     * @throws [ConnectException] The connection failed.
     */
    @Throws(ConnectException::class, CancellationException::class)
    suspend fun connect(host: String, port: Int, tls: Boolean): HttpConnection
}
