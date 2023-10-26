package io.ketty.http.client.core

/**
 * Low-level http client
 */
interface HttpClient : HttpCloseable {
    /**
     * Connects to [host]:[port]
     */
    suspend fun connect(host: String, port: Int): HttpConnection
}
