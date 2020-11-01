import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch {
        val a = taskA()
        println("After a")
        val b = taskB()
        println("After b")
        println("Result ${a + b}")
    }


//    GlobalScope.launch {
//        val user = getUserByIdAsync(1)
//        val users = getUsersAsync()
//        val result = checkIfExists(user.await(), users.await())
//        println(result)
//    }
//    println("Done")
    Thread.sleep(10_000)
}

private fun getUserByIdAsync(id: Int) = GlobalScope.async {
    println("Get user by id")
    delay(2_000)
    User(id, "Jan", "Kowalski")
}

private fun getUsersAsync() = GlobalScope.async {
    println("Get users")
    delay(3_000)
    listOf(
            User(1, "Jan", "Kowalski"),
            User(2, "Marek", "Nowak"),
            User(2, "Maria", "Nowak")
    )
}

private fun checkIfExists(user: User, users: List<User>) = user in users

data class User(val id: Int, val firstName: String, val lastName: String)

suspend fun taskA(): Int {
    delay(2_000)
    return 1
}

suspend fun taskB(): Int {
    delay(2_000)
    return 2
}