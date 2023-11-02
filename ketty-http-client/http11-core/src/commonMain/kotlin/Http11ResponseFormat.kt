package io.ketty.http.client.http11.core

/**
 * Format for reading entities of a `Response` ([RFC 2068, Section 6](https://www.rfc-editor.org/rfc/rfc2068#section-6))
 */
interface Http11ResponseFormat {
    /**
     * Read a `Status-Line` ([RFC 2068, Section 6.1](https://www.rfc-editor.org/rfc/rfc2068#section-6.1))
     *
     * @return [Pair] with the status code and status phrase
     */
    fun readStatusLine(line: String): Pair<Int, String>

    /**
     * Read all `message-header` entities ([RFC 2068, Section 4.2](https://www.rfc-editor.org/rfc/rfc2068#section-4.2))
     *
     * @return [List] All read headers. Maybe empty.
     */
    fun readHeaders(lines: List<String>): List<Pair<String, String>>
}
