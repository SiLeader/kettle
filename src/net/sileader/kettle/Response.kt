package net.sileader.kettle

import com.sun.net.httpserver.HttpExchange

class Response(private val exchange: HttpExchange) {
    private var isEndSession = false

    var status = 200
    var body = ""
    val headers = mutableMapOf("Server" to "Kettle")
    val isEnd get() = isEndSession

    fun end() {
        headers.forEach {k, v -> exchange.responseHeaders.add(k, v)}
        exchange.sendResponseHeaders(status, body.length.toLong())
        val writer = exchange.responseBody.bufferedWriter()
        writer.write(body)
        writer.close()

        isEndSession = true
    }

    fun end(body: String) {
        this.body = body
        end()
    }
}