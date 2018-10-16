package net.sileader.kettle

class Path(path: String, val operation: Operation) {
    val pathPattern: Regex
    val patternInfo: List<String>

    init {
        val variableRegex = Regex("""<(\w+)>""")
        val result = variableRegex.findAll(path)
        patternInfo = result.map { it.groupValues[1] }.toList()
        pathPattern = variableRegex.replace(path, "(\\\\w+)").toRegex()
    }
}