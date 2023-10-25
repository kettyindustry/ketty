package io.ketty.http.client.core.request

/**
 * A request/response body writable
 */
sealed class OutgoingBody {
    /**
     * Outgoing body with no content
     */
    data object Empty : OutgoingBody()

    /**
     * The incoming body is represented as a [ByteArray]
     */
    abstract class Binary : OutgoingBody() {
        /**
         * Get request body as [ByteArray]
         * @return The request body
         */
        abstract fun bytes(): ByteArray
    }
}
