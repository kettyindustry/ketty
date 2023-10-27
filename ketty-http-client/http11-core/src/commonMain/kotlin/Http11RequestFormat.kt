package io.ketty.http.client.http11.core

/**
 * Format for writing entities in a `Request`.
 */
interface Http11RequestFormat {
    /**
     * Write a `Request-Line` as described in [RFC 2068, Section 5.1](https://www.rfc-editor.org/rfc/rfc2068#section-5.1)
     */
    fun writeRequestLine(method: Http11Method, requestUri: String)

    /**
     * Write one `message-header` entity as described in [RFC 2068, Section 4.2](https://www.rfc-editor.org/rfc/rfc2068#section-4.2)
     *
     * It neither validates the header name nor the header value
     */
    fun writeHeader(fieldName: String, fieldValue: String)

    /**
     * Write a `CRLF` as described in [RFC 2068, Section 2.2](https://www.rfc-editor.org/rfc/rfc2068#section-2.2)
     */
    fun writeNewLine()
}
