package pl.training.goodweather

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import pl.training.goodweather.configuration.ApplicationDatabase

@RunWith(AndroidJUnit4::class)
open class DatabaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    val databaseName = "database"

    lateinit var database: ApplicationDatabase

    @CallSuper
    @Before
    open fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, ApplicationDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @CallSuper
    @After
    open fun teardown() {
        database.close()
    }

}