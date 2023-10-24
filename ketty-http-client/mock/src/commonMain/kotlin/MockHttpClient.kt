package io.ketty.http.client.mock

import io.ketty.http.client.core.HttpClient
import io.ketty.http.client.core.request.HttpRequestMessage
import io.ketty.http.client.core.request.HttpResponseMessage

class MockHttpClient(private val engine: MockEngine) : HttpClient {
    override suspend fun execute(message: HttpRequestMessage): HttpResponseMessage = this.engine.mock(message)
    override fun close() = Unit
}
