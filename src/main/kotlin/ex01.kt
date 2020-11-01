import kotlinx.coroutines.*

fun main() {
    (1..10000).forEach { index ->
        GlobalScope.launch {
            val name = Thread.currentThread().name
            println("$index printed on thread $name")
        }
    }
    Thread.sleep(1000)
}