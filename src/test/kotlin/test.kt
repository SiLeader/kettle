import net.sileader.kettle.*
import net.sileader.kettle.operations.ContinuousRoutingOperation
import net.sileader.kettle.operations.FileOperation

class MainOperation : Operation() {
    override fun get(request: Request, response: Response) {
        response.body = "hello world"
        response.end()
    }
}

class PatternOperation : Operation() {
    override fun get(request: Request, response: Response) {
        response.body = "req: a: ${request.params["a"]}"
        response.end()
    }
}

class PathOperation : Operation() {
    override fun get(request: Request, response: Response) {
        response.body = "req: a: ${request.params["path"]}"
        response.end()
    }
}

fun main(args: Array<String>) {
    Kettle(
            8080,
            Router(
                    "/" to MainOperation(),
                    "/hello" to MainOperation(),
                    "/hello/world" to MainOperation(),
                    "/h/<a>" to PatternOperation(),
                    inThe("/kettle") to PathOperation(),
                    inThe("/con") to ContinuousRoutingOperation(
                            Router(
                                    "/" to MainOperation(),
                                    "/hello" to MainOperation(),
                                    "/hello/world" to MainOperation(),
                                    "/h/<a>" to PatternOperation(),
                                    inThe("/kettle") to PathOperation()
                            )
                    ),
                    inThe("/file") to FileOperation("test_html")
            )
    ).run()
}
