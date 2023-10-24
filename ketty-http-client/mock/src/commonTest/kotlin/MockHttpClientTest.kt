package io.ketty.http.client.mock

import io.ketty.http.client.core.request.HttpRequestMessage
import io.ketty.http.client.core.request.HttpResponseMessage
import io.ketty.http.client.core.request.IncomingBody
import io.ketty.http.client.core.request.OutgoingBody
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MockHttpClientTest {
    /**
     * Verifies that the request and the response are not altered during execution.
     */
    @Test
    fun integrity() = runTest {
        val request = HttpRequestMessage(
            method = "GET",
            url = "https://localhost/",
            headers = mapOf("x-client-key" to listOf("client value")),
            body = OutgoingBody.Empty
        )
        val response = HttpResponseMessage(
            statusCode = 200, headers = mapOf("x-server-key" to listOf("server value")), body = IncomingBody.Empty
        )

        val httpClient = MockHttpClient {
            assertEquals(request, it, "Malformed request message")
            response
        }

        val actualResponse = httpClient.execute(request)
        assertEquals(response, actualResponse, "Malformed response message")

        httpClient.close()
    }

    /**
     * Verifies that the request is executed only once
     */
    @Test
    fun once() = runTest {
        val response = HttpResponseMessage(200, emptyMap(), IncomingBody.Empty)

        var called = false
        val httpClient = MockHttpClient {
            assertFalse(called, "request has been called twice")
            called = true
            response
        }

        assertFalse(called, "a request has been called during initialization")
        httpClient.execute(HttpRequestMessage("", "", emptyMap(), OutgoingBody.Empty))
        assertTrue(called, "the request has not been called")

        httpClient.close()
    }
}
