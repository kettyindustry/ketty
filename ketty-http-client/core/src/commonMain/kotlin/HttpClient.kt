package io.ketty.http.client.core

import io.ketty.http.client.core.request.HttpRequestException
import io.ketty.http.client.core.request.HttpRequestMessage
import io.ketty.http.client.core.request.HttpResponseMessage
import kotlin.coroutines.cancellation.CancellationException

/**
 * A multiplatform asynchronous HTTP client, which allow you to make requests and receive responses.
 */
interface HttpClient {
    /**
     * Executes the given [message] and returns the response
     * @param [message] [HttpRequestMessage] the request data
     * @response [HttpResponseMessage] the response data
     * @throws [CancellationException] if the execution context is canceled
     * @throws [HttpRequestException] if the request did not succeed
     */
    @Throws(CancellationException::class, HttpRequestException::class)
    suspend fun execute(message: HttpRequestMessage): HttpResponseMessage

    /**
     * Close the underlying resources
     */
    fun close()
}
