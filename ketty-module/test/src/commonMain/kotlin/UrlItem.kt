package io.ketty.module.test

import io.ketty.module.core.Item
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("http url")
data class UrlItem(private val url: String) : Item.Http() {
    override suspend fun url(): String = url
}
