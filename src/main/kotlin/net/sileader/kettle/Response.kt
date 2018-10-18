package net.sileader.kettle

import com.sun.net.httpserver.HttpExchange

class Response(private val exchange: HttpExchange, serverName: String) {
    private var isEndSession = false

    var status = 200
    var body = ""
    private val mHeaders = mutableMapOf<String, String>()
    val isEnd get() = isEndSession

    init {
        addHeader("Server", serverName)
    }

    fun addHeader(key: String, value: String) {
        mHeaders[key.toLowerCase()] = value
    }

    fun end() {
        mHeaders.forEach {k, v -> exchange.responseHeaders.add(k, v)}
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