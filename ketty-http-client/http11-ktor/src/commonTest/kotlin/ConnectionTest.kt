package io.ketty.http.client.http11.ktor

import io.ketty.http.client.core.ConnectException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ConnectionTest {
    @Test
    fun `invalid host`() = runTest {
        val httpClient = KtorHttp11Client(executionContext = this.coroutineContext)

        assertFailsWith<ConnectException> {
            httpClient.connect("", 80, false)
        }

        httpClient.close()
    }

    // TODO : Create connection test
}
