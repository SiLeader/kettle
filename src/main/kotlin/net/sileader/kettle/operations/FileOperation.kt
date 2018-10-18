package net.sileader.kettle.operations

import net.sileader.kettle.Operation
import net.sileader.kettle.Request
import net.sileader.kettle.Response
import java.io.File
import java.io.FileNotFoundException
import java.util.concurrent.*

class FileOperation(val documentRoot: String, val ignorePattern: Regex, val contentTypes: Map<String, String>) : Operation() {
    companion object {
        val DEFAULT_CONTENT_TYPE = mapOf(
                "html" to "text/html",
                "css" to "text/css",
                "js" to "application/javascript",
                "txt" to "text/plain"
        )
        val DEFAULT_IGNORE_PATTERN = Regex("""/\..*""")
    }

    private val mThreadPool = Executors.newCachedThreadPool()

    constructor(documentRoot: String) : this(documentRoot, DEFAULT_IGNORE_PATTERN, DEFAULT_CONTENT_TYPE)
    constructor(documentRoot: String, ignorePattern: Regex) : this(documentRoot, ignorePattern, DEFAULT_CONTENT_TYPE)
    constructor(documentRoot: String, contentTypes: Map<String, String>) : this(documentRoot, DEFAULT_IGNORE_PATTERN, contentTypes)

    override fun get(request: Request, response: Response, next: (Request, Response) -> Unit) {
        mThreadPool.execute {
            val path = "$documentRoot/${request.params["path"]}"

            if(ignorePattern.containsMatchIn(path)) {
                response.status = 404
                response.end()
            }else{
                try{
                    val file = File(path)
                    val ext = contentTypes[file.extension.trimStart('.')]?:"application/octet-stream"

                    val data = file.inputStream().bufferedReader().lines().reduce { s1, s2 -> s1 + "\n" + s2 }

                    if(data.isPresent) {
                        response.addHeader("Content-Type", ext)
                        response.end(data.get())
                    }else{
                        response.status = 404
                        response.end()
                    }
                }catch (e: FileNotFoundException) {
                    response.status = 404
                    response.end()
                }
            }

            next(request, response)
        }
    }
}