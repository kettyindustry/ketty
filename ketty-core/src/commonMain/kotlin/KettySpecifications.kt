package io.ketty.core

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

/**
 * Provides a convenient way to use Ketty.
 *
 * The items received from [items] will be processed asynchronously.
 * If the exploitation succeed, the [CheckCode] is sent to [checkCodes],
 * otherwise the caught [Throwable] is sent to [checkErrors]
 */
interface KettySpecifications : CoroutineScope {
    /**
     * Items in queue for processing
     */
    val items: SendChannel<Item>

    /**
     * Successful exploitation results
     */
    val checkCodes: ReceiveChannel<Pair<Item, CheckCode>>

    /**
     * Failed exploitation exceptions
     */
    val checkErrors: ReceiveChannel<Pair<Item, Throwable>>

    /**
     * Suspends until all items have been gotten and processed.
     *
     * When the count of collected items from [items] is equal to the count of emitted item to both [checkCodes] and [checkErrors], this function returns
     */
    suspend fun join()

    /**
     * Closes the underlying resources
     */
    suspend fun close()
}
