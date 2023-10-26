package io.ketty.core

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Item
import io.ketty.module.core.ItemUnsupportedException
import io.ketty.module.core.ItemVisitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class KettyTest {
    @Test
    fun `integrity with only success`() = runTest {
        val expectedItem: Item = Url("testing")
        val expectedCode = CheckCode.SAFE
        var successCount = 0

        testKetty(
            ItemIntegrityItemVisitor(expectedItem, SuccessItemVisitor(expectedCode)),
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
        val expectedItem: Item = Url("testing")
        val expectedCause = ItemUnsupportedException("x")
        var errorCount = 0

        testKetty(
            ItemIntegrityItemVisitor(expectedItem, FailureItemVisitor(expectedCause)),
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

    private data class SuccessItemVisitor(private val checkCode: CheckCode) : ItemVisitor {
        override val name: String = "Success"
        override val description: String = "Always returns the same check code"
        override suspend fun check(item: Item): CheckCode = checkCode
    }

    private data class FailureItemVisitor(private val cause: Throwable) : ItemVisitor {
        override val name: String = "Failure"
        override val description: String = "Always throws a throwable."
        override suspend fun check(item: Item): CheckCode = throw cause
    }

    private data class ItemIntegrityItemVisitor(private val expectedItem: Item, private val innerItemVisitor: ItemVisitor) : ItemVisitor {
        override val name: String = "Item integrity"
        override val description: String = "Ensure that the checked item is equal to a item"
        override suspend fun check(item: Item): CheckCode {
            assertEquals(this.expectedItem, item)
            return innerItemVisitor.check(item)
        }
    }

    private data class Url(val url: String) : Item.Http() {
        override suspend fun url(): String = url
    }
}
