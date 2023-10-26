package io.ketty.http.client.core

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
suspend inline fun <R> HttpCloseable.use(action: (HttpCloseable) -> R): R where R : Any {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    return try {
        action(this)
    } finally {
        close()
    }
}
