package net.sileader.kettle

abstract class Operation {
    open fun beforeRequest(request: Request, response: Response) {}

    open fun get(request: Request, response: Response) {
        response.status = 405
        response.end()
    }
    open fun post(request: Request, response: Response) {
        response.status = 405
        response.end()
    }
    open fun put(request: Request, response: Response) {
        response.status = 405
        response.end()
    }
    open fun delete(request: Request, response: Response) {
        response.status = 405
        response.end()
    }

    private val funcMap = mapOf<String, (Request, Response) -> Unit>(
            "GET" to {req, res -> this.get(req, res)},
            "POST" to {req, res -> this.post(req, res)},
            "PUT" to {req, res -> this.put(req, res)},
            "DELETE" to {req, res -> this.delete(req, res)}
    )

    fun call(request: Request, response: Response) {
        beforeRequest(request, response)
        if(response.isEnd)return

        funcMap[request.method]?.invoke(request, response)
        if(!response.isEnd) {
            response.status = 405
            response.end()
        }
    }
}