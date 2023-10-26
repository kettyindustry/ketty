package io.ketty.http.client.core

/**
 * Actual data for an HTTP request, including [method], [path], [headers] and [body].
 */
data class HttpRequestData(
    val method: String,
    val path: String,
    val headers: Map<String, List<String>> = emptyMap(),
    val body: String? = null
)
