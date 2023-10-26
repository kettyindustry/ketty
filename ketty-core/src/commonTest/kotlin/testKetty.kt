package io.ketty.core

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.Module
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

suspend fun testKetty(
    module: Module,
    items: Flow<Item>,
    successHandler: (item: Item, checkCode: CheckCode) -> Unit,
    failureHandler: (item: Item, exception: Throwable) -> Unit
) = coroutineScope {
    // Create ketty
    val ketty: KettySpecifications = Ketty(module, 10)

    // Handle successes
    val successJob = launch {
        for ((item, code) in ketty.checkCodes) {
            successHandler(item, code)
        }
    }

    // Handle failures
    val failureJob = launch {
        for ((item, exception) in ketty.checkErrors) {
            failureHandler(item, exception)
        }
    }

    // Send all items
    items.collect {
        ketty.items.send(it)
    }

    // Wait for ketty to process all sent items
    ketty.join()

    // Shutdown gracefully
    successJob.cancelAndJoin()
    failureJob.cancelAndJoin()
    ketty.close()
}
