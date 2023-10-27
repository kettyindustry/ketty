package io.ketty.http.client.http11.core

import kotlin.test.Test
import kotlin.test.assertEquals

class Http11MethodBuiltinTest {
    @Test
    fun options() {
        assertEquals("OPTIONS", Http11Method.Options.value)
    }

    @Test
    fun get() {
        assertEquals("GET", Http11Method.Get.value)
    }

    @Test
    fun head() {
        assertEquals("HEAD", Http11Method.Head.value)
    }

    @Test
    fun post() {
        assertEquals("POST", Http11Method.Post.value)
    }

    @Test
    fun put() {
        assertEquals("PUT", Http11Method.Put.value)
    }

    @Test
    fun delete() {
        assertEquals("DELETE", Http11Method.Delete.value)
    }

    @Test
    fun trace() {
        assertEquals("TRACE", Http11Method.Trace.value)
    }
}