package io.ketty.http.client.http11.core

class Http11ResponseParser : Http11ResponseFormat {
    override fun readStatusLine(line: String): Pair<Int, String> {
        val httpVersionEnd = line.indexOf(SP)
        val httpVersion = line.substring(0, httpVersionEnd)
        check(httpVersion == "HTTP/1.1") { "Invalid HTTP version : `$httpVersion`" }

        val statusCodeEnd = line.indexOf(SP, httpVersionEnd + 1)
        val statusCode = line.substring(httpVersionEnd + 1, statusCodeEnd).toInt()
        check(statusCode in 100..999) { "Status code out of range : `$statusCode`" }

        val reasonPhrase = line.substring(statusCodeEnd + 1)

        return statusCode to reasonPhrase
    }

    // Read all headers until an empty new line is reached
    override fun readHeaders(lines: List<String>): List<Pair<String, String>> =
        lines.fold(emptyList()) { headers, line ->
            check(line.isNotEmpty())

            if (line[0] != SP && line[0] != HT) {
                val intersection = line.indexOf(':').takeIf { it != -1 } ?: error("Header intersection not found")
                val name = line.substring(0, intersection)
                val value = line.substring(intersection + 1).trimStart(' ')
                headers + (name to value)
            } else {
                // Multi-line header
                val appendValue = '\n' + line.trimStart(SP, HT)
                val preHeaders = headers.dropLast(1)
                val (name, preValue) = headers.last()
                preHeaders + (name to (preValue + appendValue))
            }
        }
}

private const val CR: Char = 13.toChar()
private const val LF: Char = 10.toChar()
private const val SP: Char = 32.toChar()
private const val HT: Char = 9.toChar()
