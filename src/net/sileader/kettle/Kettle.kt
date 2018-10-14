package net.sileader.kettle

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

class Kettle(private val port: Int, private val router: Router) : HttpHandler {
    override fun handle(p0: HttpExchange?) {
        if(p0 == null)return

        val response = Response(p0)
        try {
            val method = p0.requestMethod
            val url = p0.requestURI.path
            val version = p0.protocol
            val headers = p0.requestHeaders

            val bufIn = p0.requestBody.bufferedReader()

            val payloadOpt = bufIn.lines().reduce { s1, s2 -> s1 + s2}
            val request = Request(url, method, version, headers.toMap(), if (payloadOpt.isPresent) {payloadOpt.get()}else{""})
            bufIn.close()

            router.route(request, response)
        }catch (e: Exception) {
            e.printStackTrace()
            response.status = 500
            response.end()
        }
    }

    fun run() {
        val http = HttpServer.create(InetSocketAddress(port), 0)
        http.createContext("/", this)
        http.start()
    }
}