package io.ketty.module.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An item to check for exploitation
 */
@Serializable
sealed class Item {
    /**
     * HTTP item that provides the parameters
     */
    @Serializable
    @SerialName("http")
    abstract class Http : Item() {
        /**
         * Get the item url
         */
        abstract suspend fun url(): String
    }
}
