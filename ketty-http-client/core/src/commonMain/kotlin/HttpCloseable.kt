package io.ketty.http.client.core

/**
 * An HTTP entity that uses resources under the hood.
 */
interface HttpCloseable {
    /**
     * Close the current HTTP entity and release underlying resources
     */
    suspend fun close()
}