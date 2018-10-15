package net.sileader.kettle

abstract class Operation {
    open fun beforeRequest(request: Request, response: Response) {}

    open fun get(request: Request, response: Response) {}
    open fun post(request: Request, response: Response) {}
    open fun put(request: Request, response: Response) {}
    open fun delete(request: Request, response: Response) {}

    open fun beforeRequest(request: Request, response: Response, next: (Request, Response) -> Unit) {
        beforeRequest(request, response)
        if (response.isEnd) return
        next(request, response)
    }

    open fun get(request: Request, response: Response, next: (Request, Response) -> Unit) {
        get(request, response)
        if (response.isEnd) return
        next(request, response)
    }
    open fun post(request: Request, response: Response, next: (Request, Response) -> Unit) {
        post(request, response)
        if (response.isEnd) return
        next(request, response)
    }
    open fun put(request: Request, response: Response, next: (Request, Response) -> Unit) {
        put(request, response)
        if (response.isEnd) return
        next(request, response)
    }
    open fun delete(request: Request, response: Response, next: (Request, Response) -> Unit) {
        delete(request, response)
        if (response.isEnd) return
        next(request, response)
    }

    private val funcMap = mapOf<String, (Request, Response, (Request, Response) -> Unit) -> Unit>(
            "GET" to {req, res, next -> this.get(req, res, next)},
            "POST" to {req, res, next -> this.post(req, res, next)},
            "PUT" to {req, res, next -> this.put(req, res, next)},
            "DELETE" to {req, res, next -> this.delete(req, res, next)}
    )

    fun call(request: Request, response: Response, next: (Request, Response) -> Unit, forceEndResponse: Boolean=true) {
        beforeRequest(request, response)
        if(response.isEnd)return

        funcMap[request.method]?.invoke(request, response, next)
        if(forceEndResponse && !response.isEnd) {
            response.status = 405
            response.end()
        }
    }
}