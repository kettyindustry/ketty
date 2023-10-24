package io.ketty.module.core

/**
 * An item to check for exploitation
 */
sealed class Item {
    /**
     * HTTP item that provides the parameters
     */
    abstract class Http : Item() {
        /**
         * Get the item url
         */
        abstract suspend fun url(): String
    }
}
