package net.sileader.kettle.test

import net.sileader.kettle.*

class MainOperation : Operation() {
    override fun get(request: Request, response: Response) {
        response.body = "hello world"
        response.end()
    }
}

fun main(args: Array<String>) {
    Kettle(
            8080,
            Router(
                    "/" to MainOperation(),
                    "/hello" to MainOperation(),
                    "/hello/world" to MainOperation()
            )
    ).run()
}
