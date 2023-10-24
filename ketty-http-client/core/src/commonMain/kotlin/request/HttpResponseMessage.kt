package io.ketty.http.client.core.request

data class HttpResponseMessage(
    val statusCode: Int,
    val headers: Map<String, List<String>>,
    val body: IncomingBody
)
