package io.ketty.core

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.ItemUnsupportedException
import io.ketty.module.test.FailureModule
import io.ketty.module.test.ItemIntegrityModule
import io.ketty.module.test.SuccessModule
import io.ketty.module.test.UrlItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class KettyTest {
    @Test
    fun `integrity with only success`() = runTest {
        val expectedItem: Item = UrlItem("testing")
        val expectedCode = CheckCode.SAFE
        var successCount = 0

        testKetty(
            ItemIntegrityModule(expectedItem, SuccessModule(expectedCode)),
            generateFlow(10_000, expectedItem),
            successHandler = { item, code ->
                assertEquals(expectedItem, item, "item mismatch")
                assertEquals(expectedCode, code, "code mismatch")
                successCount++
            },
            failureHandler = { _, _ ->
                fail("should not fails")
            }
        )

        assertEquals(10_000, successCount, "invalid number of error count")
    }

    @Test
    fun `integrity with only errors`() = runTest {
        val expectedItem: Item = UrlItem("testing")
        val expectedCause = ItemUnsupportedException("x")
        var errorCount = 0

        testKetty(
            ItemIntegrityModule(expectedItem, FailureModule(expectedCause)),
            generateFlow(10_000, expectedItem),
            successHandler = { _, _ ->
                fail("should not succeed")
            },
            failureHandler = { item, cause ->
                assertEquals(expectedItem, item, "item mismatch")
                assertEquals(expectedCause, cause, "cause mismatch")
                errorCount++
            }
        )

        assertEquals(10_000, errorCount, "invalid number of error count")
    }

    private fun generateFlow(times: Int, item: Item): Flow<Item> = flow {
        repeat(times) {
            emit(item)
        }
    }
}
