package io.ketty.http.client.mock

import io.ketty.http.client.core.HttpResponse
import io.ketty.http.client.mock.data.MockHttpResponseData
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.flow.Flow

/**
 * A mocked [HttpResponse]. The returned details matches [response]
 * @param [response] [MockHttpResponseData] - The response details to returns
 */
class MockHttpResponse(private val response: MockHttpResponseData) : HttpResponse {
    override suspend fun status(): Pair<Int, String> = response.statusCode to response.statusReason
    override suspend fun headers(): List<Pair<String, String>> = response.headers
    override suspend fun body(): ByteReadChannel = response.body?.let { ByteReadChannel(it) } ?: ByteReadChannel.Empty

    override suspend fun close() = Unit
}
