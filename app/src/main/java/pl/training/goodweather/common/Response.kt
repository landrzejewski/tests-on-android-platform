package pl.training.goodweather.common

sealed class Response<D, F> {

    class Success<D, F>(val data: D) : Response<D, F>()

    class Failure<D, F>(val exception: F) : Response<D, F>()

    fun <T> then(success: (D) -> T, failure: (F) -> T): T = when (this) {
        is Success -> success(data)
        is Failure -> failure(exception)
    }

}