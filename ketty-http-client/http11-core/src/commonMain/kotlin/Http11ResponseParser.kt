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

    override fun readHeader(line: String): Pair<String, String> {
        val separatorIndex = line.indexOf(":").takeIf { it != -1 } ?: error("Invalid message-header : `$line`")

        val name = line.substring(0, separatorIndex)
        val rawValue = line.substring(separatorIndex + 1)

        // TODO : Decode multi-line header
        val value = rawValue.trimStart(' ')

        return name to value
    }
}

private const val CR: Char = 13.toChar()
private const val LF: Char = 10.toChar()
private const val SP: Char = 32.toChar()
