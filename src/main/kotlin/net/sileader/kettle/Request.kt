package net.sileader.kettle

class Request(
        val url: String, val method: String, val version: String, val headers: Map<String, List<String>>,
        val payload: String,
        val params: Map<String, String>, val args: Map<String, String>, val server: Server) {

    constructor(request: Request, params: Map<String, String>)
            : this(request.url, request.method, request.version, request.headers, request.payload, params, request.args, request.server)
}
