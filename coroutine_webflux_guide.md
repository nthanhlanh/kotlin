# Kotlin Coroutine & WebFlux: Hướng dẫn cơ bản

## 1. Suspend function là gì?

-   `suspend fun` không tự chạy độc lập.
-   Chỉ có thể gọi trong **coroutine hiện tại** hoặc từ một `suspend`
    khác.
-   Để chạy được phải có **coroutine builder**: `runBlocking`, `launch`,
    `async`...

Ví dụ:

``` kotlin
suspend fun doSomething() {
    delay(1000)
    println("Done")
}

fun main() = runBlocking {
    doSomething() // ✅ chạy được trong coroutine
}
```

------------------------------------------------------------------------

## 2. Suspend chạy đồng bộ hay bất đồng bộ?

-   Suspend function **chạy đồng bộ** theo luồng coroutine hiện tại.
-   Khi gặp `suspend point` (như `delay`, call API, DB...) → coroutine
    sẽ **tạm dừng** và **nhả thread**.
-   Thread rảnh phục vụ request khác → **non-blocking**.

👉 Bạn viết code như đồng bộ, nhưng thực chất thực thi bất đồng bộ dưới
nền.

------------------------------------------------------------------------

## 3. Quan hệ cha -- con trong coroutine

-   `coroutineScope { ... }` cho phép tạo coroutine con gắn với cha.
-   Cha sẽ **chờ tất cả con hoàn thành** trước khi kết thúc.

Ví dụ:

``` kotlin
suspend fun parentWork() = coroutineScope {
    launch {
        delay(500)
        println("Child done")
    }
    println("Parent continues")
}
```

Kết quả:

    Parent continues
    Child done

------------------------------------------------------------------------

## 4. Đồng bộ vs Song song

-   Nếu gọi suspend function tuần tự → code chạy tuần tự (A xong mới tới
    B).
-   Muốn chạy song song → dùng `launch` hoặc `async`.

Ví dụ tuần tự:

``` kotlin
suspend fun parent() {
    workA()
    workB()
}
```

Ví dụ song song:

``` kotlin
suspend fun parent() = coroutineScope {
    val a = async { workA() }
    val b = async { workB() }
    println("Result = ${a.await()} + ${b.await()}")
}
```

------------------------------------------------------------------------

## 5. WebFlux + Coroutine

-   WebFlux gốc (Java Reactor) dùng `Mono`/`Flux`, non-blocking.
-   Kotlin cung cấp adapter biến thành `suspend`/`Flow` → viết code
    trông như đồng bộ.
-   Khi tới `suspend point` (DB, API...), coroutine nhả thread → Web
    server xử lý được nhiều request hơn.

Ví dụ controller:

``` kotlin
@GetMapping("/save")
suspend fun saveHandler(): String {
    saveData() // chạy tuần tự non-blocking
    return "ok"
}
```

Nếu cần song song:

``` kotlin
@GetMapping("/parallel")
suspend fun handle(): String = coroutineScope {
    val a = async { service.callA() }
    val b = async { service.callB() }
    "result = ${a.await()} + ${b.await()}"
}
```

------------------------------------------------------------------------

## 6. Kết luận

-   `suspend fun` = code tuần tự nhưng non-blocking.
-   Để chạy được `suspend` → cần coroutine (WebFlux tự tạo cho bạn trong
    request handler).
-   Muốn concurrency → dùng coroutine con (`launch`, `async`).

👉 Viết code như đồng bộ, chạy thực chất bất đồng bộ, tận dụng CPU &
memory tốt hơn so với mô hình blocking truyền thống.
