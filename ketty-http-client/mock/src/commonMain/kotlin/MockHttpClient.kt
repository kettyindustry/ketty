package io.ketty.http.client.mock

import io.ketty.http.client.core.HttpClient
import io.ketty.http.client.core.request.HttpRequestMessage
import io.ketty.http.client.core.request.HttpResponseMessage

/**
 * [HttpClient] for writing tests without a network.
 */
class MockHttpClient(private val engine: MockEngine) : HttpClient {
    /**
     * Execute [engine]
     *
     * @see [MockEngine.mock]
     */
    override suspend fun execute(message: HttpRequestMessage): HttpResponseMessage = this.engine.mock(message)

    /**
     * No effect, as [MockHttpClient] does not rely on any resources
     */
    override fun close() = Unit
}
