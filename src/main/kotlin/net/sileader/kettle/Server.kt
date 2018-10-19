package net.sileader.kettle

import java.util.concurrent.Executor

class Server(val executor: Executor) {
    fun execute(f: () -> Unit) {
        executor.execute(f)
    }
}
