package io.ketty.http.client.http11.ktor

import io.ktor.network.tls.CIOCipherSuites
import io.ktor.network.tls.TLSConfigBuilder

actual val platformTlsConfig: TLSConfigBuilder = TLSConfigBuilder().apply {
    serverName = null
    random = null // TODO : Implement very fast SecureRandom
    cipherSuites = listOf(CIOCipherSuites.TLS_RSA_WITH_AES128_CBC_SHA)
    trustManager = AcceptAllTrustManager
}
