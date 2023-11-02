package io.ketty.http.client.core

open class HttpException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)

class ConnectException(inner: Throwable? = null) : HttpException("Connect failed.", inner)
