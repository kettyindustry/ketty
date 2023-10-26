package io.ketty.http.client.core

/**
 * Represents an active HTTP connection of any version.
 */
interface HttpConnection : HttpCloseable {
    /**
     * Initiates a new HTTP request.
     *
     * The underlying version depends on the implementation.
     */
    suspend fun request(data: HttpRequestData): HttpResponse
}
