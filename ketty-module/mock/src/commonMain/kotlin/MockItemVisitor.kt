package io.ketty.module.mock

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.ItemVisitor

/**
 * [ItemVisitor] for writing tests without a specific implementation
 * @param [action] - Handle an [Item] and returns the expected [CheckCode]
 */
class MockItemVisitor(val action: (item: Item) -> CheckCode) : ItemVisitor {
    override val name: String = "Mock"
    override val description: String = "Mock module results using a callback"

    /**
     * Invoke [action]
     */
    override suspend fun check(item: Item): CheckCode = this.action(item)
}
