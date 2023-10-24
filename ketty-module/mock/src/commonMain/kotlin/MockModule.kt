package io.ketty.module.mock

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.Module

/**
 * [Module] for writing tests without a specific implementation
 * @param [action] - Handle an [Item] and returns the expected [CheckCode]
 */
class MockModule(val action: (item: Item) -> CheckCode) : Module {
    /**
     * Invoke [action]
     */
    override suspend fun check(item: Item): CheckCode = this.action(item)
}
