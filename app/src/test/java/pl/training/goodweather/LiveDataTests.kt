package pl.training.goodweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class LiveDataTests {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testViewModel() {
        val viewModel = TestViewModel()
        viewModel.addValue("kotlin")

        /*viewModel.feedUppercased.observeForever {}
        assertEquals(viewModel.feed.value, "kotlin")
        assertEquals(viewModel.feedUppercased.value, "KOTLIN")*/

        viewModel.feedUppercased.getOrWait { assertEquals("KOTLIN", it) }

    }


    class TestViewModel : ViewModel() {

        private val data = MutableLiveData<String>()

        val feed: LiveData<String> = data

        val feedUppercased = Transformations.map(data) { it.toUpperCase() }

        fun addValue(value: String) {
            data.postValue(value)
        }

    }

    fun <T> LiveData<T>.getOrWait(time: Long = 2, timeUnit: TimeUnit = TimeUnit.SECONDS, callback: (T) -> Unit = {}): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object: Observer<T> {
            override fun onChanged(nextData: T) {
                data = nextData
                callback.invoke(nextData)
                latch.countDown()
                this@getOrWait.removeObserver(this)
            }
        }
        observeForever(observer)
        if (!latch.await(time, timeUnit)) {
            removeObserver(observer)
            throw TimeoutException("LiveData value was not found")
        }
        @Suppress("UNCHECKED_CAST")
        return data as T
    }


}