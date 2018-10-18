package net.sileader.kettle.operations

import net.sileader.kettle.Operation
import net.sileader.kettle.Request
import net.sileader.kettle.Response
import net.sileader.kettle.Router

class ContinuousRoutingOperation(private val router: Router) : Operation() {
    override fun beforeRequest(request: Request, response: Response, next: (Request, Response) -> Unit) {
        var path = request.params["path"] ?: request.url
        if(path.isEmpty())path = "/"

        val req = Request(path, request.method, request.version, request.headers, request.payload, request.params, request.args)
        router.route(req, response)
    }
}
