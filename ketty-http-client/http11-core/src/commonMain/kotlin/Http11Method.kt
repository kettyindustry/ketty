package io.ketty.http.client.http11.core

/**
 * A `Method` token as described in [RFC 2068, Section 5.1.1](https://www.rfc-editor.org/rfc/rfc2068#section-5.1)
 */
data class Http11Method(val value: String) {
    companion object Standard {
        val Options = Http11Method("OPTIONS")
        val Get = Http11Method("GET")
        val Head = Http11Method("HEAD")
        val Post = Http11Method("POST")
        val Put = Http11Method("PUT")
        val Delete = Http11Method("DELETE")
        val Trace = Http11Method("TRACE")

        fun isExtensionToken(method: Http11Method): Boolean =
            method in arrayOf(Options, Get, Head, Post, Put, Delete, Trace)
    }
}
