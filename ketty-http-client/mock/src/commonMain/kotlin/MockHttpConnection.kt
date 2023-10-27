package io.ketty.http.client.mock

import io.ketty.http.client.core.HttpConnection
import io.ketty.http.client.core.HttpRequestData
import io.ketty.http.client.core.HttpResponse
import io.ketty.http.client.mock.data.MockCallback

class MockHttpConnection(
    private val callback: MockCallback
) : HttpConnection {
    override suspend fun request(data: HttpRequestData): HttpResponse =
        MockHttpResponse(this.callback.mockRequest(data))

    override suspend fun close() = Unit
}
