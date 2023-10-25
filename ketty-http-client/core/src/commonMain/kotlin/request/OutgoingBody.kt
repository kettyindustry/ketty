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
     * The incoming body is represented as a [String]
     */
    abstract class Text : OutgoingBody() {
        /**
         * Get request body as [String]
         * @return The request body
         */
        abstract fun text(): String
    }
}
