package io.ketty.http.client.http11.ktor

import io.ketty.http.client.core.HttpConnection
import io.ketty.http.client.core.HttpRequestData
import io.ketty.http.client.core.HttpResponse
import io.ketty.http.client.http11.core.Http11Method
import io.ketty.http.client.http11.core.Http11RequestBuilder
import io.ktor.network.sockets.Connection
import io.ktor.utils.io.close
import io.ktor.utils.io.writeStringUtf8

class KtorHttp11Connection(private val connection: Connection) : HttpConnection {
    /**
     * Write an `HTTP/1.1` request following [RFC 2068, Section 5](https://www.rfc-editor.org/rfc/rfc2068#section-5)
     */
    override suspend fun request(data: HttpRequestData): HttpResponse {
        val builder = Http11RequestBuilder().apply {
            // Write Request-Line
            writeRequestLine(Http11Method(data.method), data.path)

            // Write all user headers
            data.headers.forEach { (headerName, headerValues) ->
                headerValues.forEach { headerValue ->
                    writeHeader(headerName, headerValue)
                }
            }

            // Ensure Content-Length is defined
            if (!data.headers.containsKey("Content-Length")) {
                data.body?.let {
                    writeHeader("Content-Length", it.length.toString())
                }
            }

            // Terminate headers write
            writeNewLine()
        }
        connection.output.writePacket(builder.build())
        builder.release()
        connection.output.flush()

        data.body?.let {
            connection.output.writeStringUtf8(it)
            connection.output.flush()
        }

        return KtorHttp11Response(connection.input)
    }

    override suspend fun close() {
        connection.output.close()
        connection.socket.close()
    }
}
