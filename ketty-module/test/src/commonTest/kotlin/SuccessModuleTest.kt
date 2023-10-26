package io.ketty.module.test

import io.ketty.module.core.CheckCode
import io.ketty.module.core.Module
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SuccessModuleTest {
    private val data = CheckCode.entries.associateWith {
        SuccessModule(it)
    }

    /**
     * Verifies that [Module.name] and [Module.description] are both non-empty
     */
    @Test
    fun `description non-empty`() {
        data.forEach { (_, module) ->
            assertTrue(module.name.isNotEmpty(), "CheckCode name is empty")
            assertTrue(module.description.isNotEmpty(), "CheckCode description is empty")
        }
    }

    /**
     * Verifies that [Module.name] and [Module.description] does not change between check codes
     */
    @Test
    fun `description constant`() {
        var lastName: String? = null
        var lastDescription: String? = null

        data.forEach { (_, module) ->
            assertEquals(lastName ?: module.name, module.name)
            lastName = module.name
            assertNotNull(lastName)

            assertEquals(lastDescription ?: module.description, module.description)
            lastDescription = module.description
            assertNotNull(lastDescription)
        }

        assertNotNull(lastName)
        assertNotNull(lastDescription)
    }

    @Test
    fun check() = runTest {
        data.forEach { (expectedCode, module) ->
            val code = module.check(FailItem)
            assertEquals(expectedCode, code)
        }
    }
}
