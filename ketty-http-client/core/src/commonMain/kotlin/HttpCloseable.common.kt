package io.ketty.http.client.core

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
suspend inline fun <T, R> T.use(action: (T) -> R): R where T : HttpCloseable, R : Any {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    return try {
        action(this)
    } finally {
        close()
    }
}
