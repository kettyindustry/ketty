package io.ketty.module.core

/**
 * The module threw an exception during an exploitation process.
 */
class ModuleFailedException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)
