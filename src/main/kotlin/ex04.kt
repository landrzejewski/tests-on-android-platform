import kotlinx.coroutines.*

fun main() {
    val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
        println("Before first delay")
        delay(200)
        println("After first delay")
        println("Before  second delay")
        delay(500)
        println("After second delay")
    }

    GlobalScope.launch {
        println("Waiting for first job")
        job.join()
        println("Before first delay (second job)")
        delay(200)
        println("After first delay (second job)")
    }

    Thread.sleep(10_000)
}