package net.sileader.kettle

import net.sileader.kettle.paths.IncompletePath
import net.sileader.kettle.paths.SimplePath

infix fun String.to(operation: Operation): Path = SimplePath(this, operation)
infix fun Regex.to(operation: Operation): Path = IncompletePath(this, operation)

fun startsWith(path: String) = ("$path(.*)").toRegex()
fun inThe(path: String, withThisPath: Boolean=true) = if(withThisPath) startsWith(path) else startsWith("$path/")
