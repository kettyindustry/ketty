# ketty http client

Low-level HTTP client designed for fast networking and handling many connections.

## Usage

The usage is straightforward:

```kotlin
httpClient.connect("127.0.0.1", 80).use { connection ->
    connection.request(
        method = "GET",
        path = "/",
        headers = mapOf("Host" to listOf("localhost")),
        body = null
    ).use { response ->
        val (status, reason) = response.status()
        val headers = response.headers().toList()
    }
}
```
