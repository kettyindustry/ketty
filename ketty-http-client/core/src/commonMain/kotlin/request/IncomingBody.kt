package io.ketty.http.client.core.request

import kotlinx.io.Sink

/**
 * A request/response body readable
 */
sealed class IncomingBody {
    /**
     * Incoming body with no content
     */
    data object Empty : IncomingBody()

    /**
     * Incoming body is transferable to a [Sink]
     */
    abstract class Transferable : IncomingBody() {
        /**
         * Transfer to [sink]
         * @param [sink] [Sink] the sink to write the stream to
         * @param [limit] [Long] the limit of bytes to transfer
         * @return the number of bytes transferred to [sink], up to [limit].
         */
        abstract suspend fun transferTo(sink: Sink, limit: Long = Long.MAX_VALUE): Long
    }
}
