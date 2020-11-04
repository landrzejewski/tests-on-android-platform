package pl.training.goodweather.common

import android.database.DatabaseUtils
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.TimeUnit

class RxTests {

    @Test
    fun `test concat`() {
        val observableOne = Observable.just(1)
        val observableTwo = Observable.just(2)
        val resultObservable = observableOne.concatWith(observableTwo)
        resultObservable.test()
            .assertResult(1, 2)
            .assertComplete()
    }



    @Test
    fun `test amb`() {
        val scheduler = TestScheduler()
        val one = Observable.interval(1, TimeUnit.SECONDS, scheduler)
            .take(5)
            .map { it + 1 }
        val two = Observable.interval(2, TimeUnit.SECONDS, scheduler)
            .take(5)
            .map { it * 2 }
        val result = one.ambWith(two).test()
        scheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        result
            .assertValueCount(2)
        scheduler.advanceTimeBy(10, TimeUnit.SECONDS)
        result
            .assertComplete()
    }

    @Test
    fun `trampoline scheduler`() {
        Observable.interval(1, TimeUnit.SECONDS, Schedulers.trampoline())
            .take(5)
            .test()
            .assertValueCount(5)
    }

    @Test
    fun `test button is enabled after 10 photos`() {
        val subject = PublishSubject.create<Photo>()
        val photoProvider = object: PhotoProvider {

            override fun get(): Observable<Photo>  = subject

        }
        val viewMode = PhotoViewModel(photoProvider)
        subject.onNext(Photo())
        assertFalse(viewMode.loadMoreButtonVisible)
        subject.onNext(Photo())
        subject.onNext(Photo())
        subject.onNext(Photo())
        subject.onNext(Photo())
        subject.onNext(Photo())
        subject.onNext(Photo())
        subject.onNext(Photo())
        subject.onNext(Photo())
        subject.onNext(Photo())
        assertTrue(viewMode.loadMoreButtonVisible)
    }


    class Photo

    interface PhotoProvider {

        fun get(): Observable<Photo>

    }

    class PhotoViewModel(private val photoProvider: PhotoProvider) {

        private var photos = arrayListOf<Photo>()
        var loadMoreButtonVisible = false

        init {
            photoProvider.get()
                .subscribe {
                    photos.add(it)
                    if (photos.size >= 10) {
                        loadMoreButtonVisible = true
                    }
                }
        }

    }

}