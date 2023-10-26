package io.ketty.http.client.core

import kotlinx.coroutines.flow.Flow

/**
 * An HTTP response that is being received
 */
interface HttpResponse : HttpCloseable {
    /**
     * Receive headers in a [Flow]
     */
    suspend fun headers(): Flow<Pair<String, String>>
}
