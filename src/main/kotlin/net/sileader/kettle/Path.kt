package net.sileader.kettle

abstract class Path(val pathPattern: Regex, val operation: Operation, val patternInfo: List<String>)
