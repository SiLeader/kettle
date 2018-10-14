package net.sileader.kettle

class Request(val url: String, val method: String, val version: String, val headers: Map<String, List<String>>, val payload: String)