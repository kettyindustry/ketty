package io.ketty.http.client.mock.data

import io.ketty.http.client.core.HttpRequestData
import io.ketty.http.client.mock.MockHttpClient

/**
 * Callback used by [MockHttpClient].
 * It can handle new HTTP connections with [mockConnection].
 * It can handle HTTP requests with [mockRequest]
 */
fun interface MockCallback {
    /**
     * Handle a new HTTP connection
     * @param [host] [String] - The address host
     * @param [port] [Int] - The address port
     */
    fun mockConnection(host: String, port: Int) = Unit

    /**
     * Handle a new HTTP request
     * @param [request] [HttpRequestData] - the sent HTTP request data
     * @return [MockHttpResponseData] - The fake response data
     */
    fun mockRequest(request: HttpRequestData): MockHttpResponseData
}
