package io.ketty.http.client.mock

import io.ketty.http.client.core.request.HttpRequestMessage
import io.ketty.http.client.core.request.HttpResponseMessage

fun interface MockEngine {
    fun mock(message: HttpRequestMessage): HttpResponseMessage
}
