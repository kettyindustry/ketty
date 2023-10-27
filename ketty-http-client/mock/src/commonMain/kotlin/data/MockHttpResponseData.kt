package io.ketty.http.client.mock.data

import io.ketty.http.client.mock.MockHttpClient
import kotlinx.coroutines.flow.Flow

/**
 * HTTP response to returns in [MockHttpClient]. It includes [statusCode], [statusReason], [headers] and [body]
 */
data class MockHttpResponseData(
    val statusCode: Int,
    val statusReason: String,
    val headers: List<Pair<String, String>>,
    val body: String?
)
