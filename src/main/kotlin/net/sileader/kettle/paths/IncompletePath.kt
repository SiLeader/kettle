package net.sileader.kettle.paths

import net.sileader.kettle.Operation
import net.sileader.kettle.Path

class IncompletePath(regex: Regex, operation: Operation) : Path(regex, operation, listOf("path"))
