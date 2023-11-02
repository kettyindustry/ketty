package io.ketty.http.client.http11.ktor

import io.ktor.network.tls.TLSConfigBuilder

actual val platformTlsConfig: TLSConfigBuilder = TLSConfigBuilder().apply {
    serverName = null
}
