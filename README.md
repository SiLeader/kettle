# Kettle: Simple Web Micro framework

&copy; 2018 SiLeader.

## features
+ Easy to create RESTful API
+ Return files

## usage
```kotlin
fun main(args: Array<String>) {
    Kettle(
            8080,
            Router(
                    "/" to MainOperation(),
                    "/hello" to MainOperation(),
                    "/hello/world" to MainOperation(),
                    "/h/<a>" to PatternOperation(),
                    inThe("/kettle") to PathOperation(),
                    inThe("/con") to ContinuousRoutingOperation(
                            Router(
                                    "/" to MainOperation(),
                                    "/hello" to MainOperation(),
                                    "/hello/world" to MainOperation(),
                                    "/h/<a>" to PatternOperation(),
                                    inThe("/kettle") to PathOperation()
                            )
                    ),
                    inThe("/file") to FileOperation("test_html")
            )
    ).run()
}
```

1. Create `Operation` implementation.
1. Create `Router` and `Kettle` instances.

### Operation
`Operation` class is a base class to process request from clients.

This class has `get`, `post`, `put`, `delete`, and `beforeRequest` methods.
If you want to process GET requests, you must override `get` method.