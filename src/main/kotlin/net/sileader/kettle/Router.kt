package net.sileader.kettle

open class Router(private vararg val path: Path) {
    fun route(request: Request, response: Response) {
        run loop@ {
            path.forEach {
                path ->
                val match = path.pathPattern.matchEntire(request.url)

                if(match != null) {
                    val params = if (match.groupValues.isNotEmpty()) {
                        match.groupValues.drop(1).zip(path.patternInfo) { m, p -> Pair(p, m)}.toMap()
                    }else{
                        mapOf()
                    }

                    path.operation.call(
                            Request(request, params),
                            response,
                            {
                                _, response ->
                                if (response.isEnd)return@call
                                response.status = 405
                                response.end()
                            }
                    )
                    return@loop
                }
            }
            response.status = 404
            response.end()
        }
    }
}