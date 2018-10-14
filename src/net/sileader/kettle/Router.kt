package net.sileader.kettle

class Router(private vararg val path: Path) {
    fun route(request: Request, response: Response) {
        run loop@ {
            path.forEach {
                path ->
                if(path.path == request.url) {
                    path.operation.call(request, response)
                    return@loop
                }
            }

            response.status = 404
            response.end()
        }
    }
}