package io.ketty.http.client.http11.ktor

import io.ketty.http.client.core.HttpResponse
import io.ketty.http.client.http11.core.Http11ResponseParser
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line

/**
 * `HTTP/1.1` implementation for [HttpResponse]
 */
class KtorHttp11Response(private val channel: ByteReadChannel) : HttpResponse {
    private companion object {
        val parser = Http11ResponseParser()
    }

    /**
     * Reads a `Status-Line` following [RFC 2068, Section 6.1](https://www.rfc-editor.org/rfc/rfc2068#section-6.1)
     */
    override suspend fun status(): Pair<Int, String> {
        val line = channel.readUTF8Line() ?: error("Response channel was closed while reading status")
        return parser.readStatusLine(line)
    }

    override suspend fun headers(): List<Pair<String, String>> {
        // TODO : Case-insensitive headers

        val lines = buildList {
            while (true) {
                val line = channel.readUTF8Line()

                when {
                    line == null -> error("Response channel closed after reading $size headers")
                    line.isEmpty() -> break
                    else -> add(line)
                }
            }
        }

        return parser.readHeaders(lines)
    }

    override suspend fun body(): ByteReadChannel {
        return channel
    }

    override suspend fun close() {
        //
    }
}
