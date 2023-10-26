package io.ketty.module.test

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.Module
import kotlin.test.assertEquals

data class ItemIntegrityModule(private val expectedItem: Item, private val innerModule: Module) : Module {
    override val name: String = "Item integrity"
    override val description: String = "Ensure that the checked item is equal to a item"
    override suspend fun check(item: Item): CheckCode {
        assertEquals(this.expectedItem, item, "Item mismatch")
        return innerModule.check(item)
    }
}
