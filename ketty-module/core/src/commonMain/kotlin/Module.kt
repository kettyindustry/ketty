package io.ketty.module.core

import kotlin.coroutines.cancellation.CancellationException

/**
 * Serves as the base interface for all modules.
 * It provides a common interface for interacting with modules at the most basic level.
 */
interface Module {
    /**
     * Verifies if [item] is exploitable
     * @param [item] [Item] The targeted item
     * @return [CheckCode] Check code of [item]
     * @throws [ModuleFailedException] if an error occurs, if [item] is compatible but malformed, for example
     * @throws [ItemUnsupportedException] if [item] is not supported
     * @throws [CancellationException] if check is canceled during execution
     */
    @Throws(ModuleFailedException::class, ItemUnsupportedException::class, CancellationException::class)
    suspend fun check(item: Item): CheckCode
}
