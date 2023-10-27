package io.ketty.http.client.core

import io.ktor.utils.io.ByteReadChannel

/**
 * An HTTP response that is being received
 */
interface HttpResponse : HttpCloseable {
    /**
     * Receive status
     */
    suspend fun status(): Pair<Int, String>

    /**
     * Receive headers as a collection
     */
    suspend fun headers(): List<Pair<String, String>>

    /**
     * Receive body as a [ByteReadChannel]
     * @return [ByteReadChannel] - Channel to read the response body
     */
    suspend fun body(): ByteReadChannel
}
