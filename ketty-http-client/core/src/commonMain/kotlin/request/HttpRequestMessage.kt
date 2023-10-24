package io.ketty.http.client.core.request

data class HttpRequestMessage(
    val method: String,
    val url: String,
    val headers: Map<String, List<String>>,
    val body: OutgoingBody
)
