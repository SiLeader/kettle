package net.sileader.kettle

import net.sileader.kettle.paths.IncompletePath
import net.sileader.kettle.paths.SimplePath

infix fun String.to(operation: Operation): Path = SimplePath(this, operation)
infix fun Regex.to(operation: Operation): Path = IncompletePath(this, operation)

fun startsWith(path: String) = Regex("$path(.*)")
fun inThe(path: String) = Regex("$path(/.*)?")
