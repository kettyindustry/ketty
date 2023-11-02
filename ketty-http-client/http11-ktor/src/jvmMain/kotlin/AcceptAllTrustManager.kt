package io.ketty.http.client.http11.ktor

import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

object AcceptAllTrustManager : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
    override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
}
