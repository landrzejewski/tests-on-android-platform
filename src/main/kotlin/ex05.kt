import kotlinx.coroutines.*

fun main() {
    var isOpen = false

    GlobalScope.launch {
        println("Before delay")
        delay(500)
        println("After delay")
        isOpen = true
    }

    GlobalScope.launch {
        repeat(3) {
            println("Checking isOpen")
            delay(800)
            println("Status: $isOpen")
        }
    }
    Thread.sleep(1000)
}