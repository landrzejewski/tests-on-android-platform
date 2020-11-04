package pl.training.goodweather

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.mockito.Mockito
import pl.training.goodweather.configuration.ApplicationDatabase
import pl.training.goodweather.forecast.model.Forecast
import pl.training.goodweather.forecast.model.Weather
import pl.training.goodweather.forecast.model.WeatherInteractor
import pl.training.goodweather.forecast.model.api.*
import pl.training.goodweather.forecast.model.database.RoomWeatherRepository
import pl.training.goodweather.forecast.model.database.WeatherRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val cityName = "warsaw"
    private val preferencesName = "TEST_PREFERENCES"
    private val weatherFromBackend = Weather(1, cityName, listOf(Forecast(2, Date(), "", 0, 0, "")))
    private val weatherTo = WeatherTo(
        CityTo(1, cityName, CoordinatesTo(0F,0F), ""), listOf(
        ForecastTo(1L, TemperatureTo(1F, 1F, 1F, 1F, 1F, 1F), 1F, 1, listOf(SummaryTo(1L, "", "", "")), 1F, 1, 1, 1F)
        )
    )


    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("pl.training.goodweather", appContext.packageName)
    }

    @Test
    fun testSharedPreferences() {
        val key = "language"
        val value = "kotlin"
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val preferences = appContext.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()

        val result = appContext.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
            .getString(key, null)
        assertEquals(value, result)
    }

    @Test
    fun testDatabase() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val database = Room.inMemoryDatabaseBuilder(appContext, ApplicationDatabase::class.java).build()
        val weatherDao = database.weatherDao()
        val weatherRepository = RoomWeatherRepository(weatherDao, Schedulers.trampoline())
        val weatherProvider = Mockito.mock(WeatherProvider::class.java)
        whenever(weatherProvider.getWeather(cityName)).thenReturn(Single.just(weatherTo))
        val apiMappers = ApiMappers()
        val weatherInteractor = WeatherInteractor(weatherProvider, weatherRepository, apiMappers, Schedulers.trampoline())

        weatherInteractor.getWeather(cityName).test()
            .assertResult(apiMappers.toDomainModel(weatherTo))
            .assertValueCount(1)
            .assertComplete()
     }


}