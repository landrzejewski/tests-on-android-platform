import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch {
        println("Before delay")
        delay(500)
        println("After delay")
    }
    Thread.sleep(1000)
}