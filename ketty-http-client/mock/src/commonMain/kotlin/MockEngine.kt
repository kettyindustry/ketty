package io.ketty.http.client.mock

import io.ketty.http.client.core.request.HttpRequestMessage
import io.ketty.http.client.core.request.HttpResponseMessage

/**
 * Http request handler for [MockHttpClient]
 *
 * @see [MockHttpClient]
 */
fun interface MockEngine {
    /**
     * Handle a request [message] and respond with a [HttpResponseMessage]
     * @param [message] [HttpRequestMessage] the received request
     * @return [HttpResponseMessage] the expected response
     */
    fun mock(message: HttpRequestMessage): HttpResponseMessage
}
