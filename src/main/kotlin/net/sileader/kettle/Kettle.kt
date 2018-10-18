package net.sileader.kettle

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

class Kettle(private val port: Int, private val router: Router, private val beforeRequest: Operation?=null) : HttpHandler {
    override fun handle(p0: HttpExchange?) {
        if(p0 == null)return

        val response = Response(p0, "Kettle $VERSION")
        try {
            val method = p0.requestMethod
            val url = p0.requestURI.path
            val query = p0.requestURI.query
            val version = p0.protocol
            val headers = p0.requestHeaders

            val args = if(!query.isNullOrEmpty()) {
                query.split("&").asSequence().map { it.split("=") }.map { Pair(it[0], it[1]) }.toList()
            }else{
                listOf()
            }

            val bufIn = p0.requestBody.bufferedReader()

            val payloadOpt = bufIn.lines().reduce { s1, s2 -> s1 + s2}
            val request = Request(url, method, version, headers.toMap(), if (payloadOpt.isPresent) {payloadOpt.get()}else{""}, mapOf(), args.toMap())
            bufIn.close()

            if(beforeRequest != null) {
                beforeRequest.beforeRequest(
                        request,
                        response
                ) {
                    req, res ->
                    if(res.isEnd)return@beforeRequest
                    beforeRequest.call(
                            req,
                            res,
                            {
                                rq, rs ->
                                router.route(rq, rs)
                            },
                            forceEndResponse = false
                    )
                }
            }else{
                router.route(request, response)
            }
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