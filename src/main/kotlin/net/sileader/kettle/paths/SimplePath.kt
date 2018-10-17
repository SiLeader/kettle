package net.sileader.kettle.paths

import net.sileader.kettle.Operation
import net.sileader.kettle.Path

class SimplePath(path: String, operation: Operation) : Path(pathPattern(path), operation, patternInfo(path)) {
    companion object {
        private val VARIABLE_REGEX = Regex("""<(\w+)>""")

        private fun patternInfo(path: String): List<String> {
            val result = VARIABLE_REGEX.findAll(path)
            return result.map { it.groupValues[1] }.toList()
        }
        private fun pathPattern(path: String): Regex {
            return VARIABLE_REGEX.replace(path, "(\\\\w+)").toRegex()
        }
    }
}