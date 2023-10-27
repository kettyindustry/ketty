package io.ketty.http.client.http11.core

import io.ktor.utils.io.core.BytePacketBuilder
import io.ktor.utils.io.core.ByteReadPacket
import io.ktor.utils.io.core.writeText

class Http11RequestBuilder : Http11RequestFormat {
    private val packet = BytePacketBuilder()

    override fun writeRequestLine(method: Http11Method, requestUri: String) {
        packet.writeText(method.value)
        packet.writeByte(SP)
        packet.writeText(requestUri)
        packet.writeByte(SP)
        packet.writeText("HTTP/1.1")
        packet.writeByte(CR)
        packet.writeByte(LF)
    }

    override fun writeHeader(fieldName: String, fieldValue: String) {
        packet.writeText(fieldName)
        packet.writeText(":")
        packet.writeByte(SP)
        packet.writeText(fieldValue)
        packet.writeByte(CR)
        packet.writeByte(LF)
    }

    override fun writeNewLine() {
        packet.writeByte(CR)
        packet.writeByte(LF)
    }

    /**
     * Build request packet
     * @return Built request packet
     */
    fun build(): ByteReadPacket = packet.build()

    /**
     * Release underlying buffer
     */
    fun release() = packet.release()
}

private const val CR: Byte = 13
private const val LF: Byte = 10
private const val SP: Byte = 32

