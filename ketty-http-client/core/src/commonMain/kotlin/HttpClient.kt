package io.ketty.http.client.core

/**
 * Low-level http client
 */
interface HttpClient : HttpCloseable {
    /**
     * Connects to [host]:[port].
     * If [tls] is enabled then a TLS connection is made.
     */
    suspend fun connect(host: String, port: Int, tls: Boolean): HttpConnection
}
