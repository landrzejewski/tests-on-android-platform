import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch(Dispatchers.Main) {
        val name = Thread.currentThread().name
        println("Printed on thread $name")
    }
    Thread.sleep(10_000)
}