package io.ketty.http.client.mock

import io.ketty.http.client.core.HttpRequestData
import io.ketty.http.client.core.use
import io.ketty.http.client.mock.data.MockCallback
import io.ketty.http.client.mock.data.MockHttpResponseData
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Verifies that [MockCallback] is invoked properly
 */
class MockCallbackTest {
    private val expectedHost = "127.0.0.1"
    private val expectedPort = 443

    /**
     * Ensure that [MockCallback.mockConnection] is invoked with the right parameters and only one time per [MockHttpClient.connect] invocation
     */
    @Test
    fun connection() = runTest {
        val callback = object : MockCallback {
            var mocked = false

            override fun mockConnection(host: String, port: Int) {
                assertFalse(mocked)
                assertEquals(expectedHost, host)
                assertEquals(expectedPort, port)
                mocked = true
            }

            override fun mockRequest(request: HttpRequestData): MockHttpResponseData =
                fail("Should not mock request")
        }

        assertFalse(callback.mocked)
        MockHttpClient(callback).use { httpClient ->
            assertFalse(callback.mocked)
            httpClient.connect(expectedHost, expectedPort).use {
                assertTrue(callback.mocked)
            }
        }
        assertTrue(callback.mocked)
    }

    /**
     * Ensure that [MockCallback.mockRequest] is invoked with the right parameters and only one time per [MockHttpConnection.request] invocation
     */
    @Test
    fun request() = runTest {
        val expectedRequest = HttpRequestData("GET", "/")
        val expectedResponse = MockHttpResponseData(200, "OK", emptyFlow(), "testing")

        val callback = object : MockCallback {
            var mocked = false

            override fun mockConnection(host: String, port: Int) = fail("Should not mock connection")

            override fun mockRequest(request: HttpRequestData): MockHttpResponseData {
                assertFalse(mocked)
                assertEquals(expectedRequest, request)
                mocked = true
                return expectedResponse
            }
        }

        assertFalse(callback.mocked)
        MockHttpConnection(callback).use { connection ->
            assertFalse(callback.mocked)
            connection.request(expectedRequest).use { response ->
                assertTrue(callback.mocked)
                val (code, reason) = response.status()
                assertEquals(expectedResponse.statusCode, code)
                assertEquals(expectedResponse.statusReason, reason)
                val headers = response.headers()
                assertContentEquals(expectedResponse.headers.toList(), headers.toList())
                val body = response.body()
                assertEquals(expectedResponse.body, body.readRemaining().readText())
            }
            assertTrue(callback.mocked)
        }
        assertTrue(callback.mocked)
    }
}
