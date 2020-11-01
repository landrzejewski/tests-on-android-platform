import kotlinx.coroutines.*

fun main() {
    val job = GlobalScope.launch {
        delay(200)
        println("Parent is ready")
        delay(200)
    }
    GlobalScope.launch(job) {
        delay(200)
        println("Child is ready")
        delay(200)
    }
    if (job.children.iterator().hasNext()) {
        println("Has children ${job.children}")
    } else {
        println("Job has no children")
    }
    Thread.sleep(10_000)
}