package net.sileader.kettle

infix fun String.to(operation: Operation): Path = Path(this, operation)
