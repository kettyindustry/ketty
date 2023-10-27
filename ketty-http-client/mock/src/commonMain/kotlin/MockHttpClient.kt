package io.ketty.http.client.mock

import io.ketty.http.client.core.HttpClient
import io.ketty.http.client.core.HttpConnection
import io.ketty.http.client.mock.data.MockCallback

/**
 * A fake [HttpClient] that uses [callback] to process connections and responses
 * @param [callback] [MockCallback] - Callback used to handle connections, requests and responses
 */
class MockHttpClient(private val callback: MockCallback) : HttpClient {
    override suspend fun connect(host: String, port: Int, tls: Boolean): HttpConnection {
        callback.mockConnection(host, port, tls)
        return MockHttpConnection(callback)
    }

    override suspend fun close() = Unit
}
