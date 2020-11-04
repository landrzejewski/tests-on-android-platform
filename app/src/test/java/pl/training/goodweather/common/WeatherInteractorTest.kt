package pl.training.goodweather.common

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import pl.training.goodweather.forecast.model.Forecast
import pl.training.goodweather.forecast.model.Weather
import pl.training.goodweather.forecast.model.WeatherInteractor
import pl.training.goodweather.forecast.model.api.*
import pl.training.goodweather.forecast.model.database.WeatherRepository
import java.lang.IllegalArgumentException
import java.util.*

class WeatherInteractorTest {

    lateinit var weatherProvider: WeatherProvider
    lateinit var weatherRepository: WeatherRepository
    lateinit var apiMappers: ApiMappers
    lateinit var weatherInteractor: WeatherInteractor

    private val cityName = "warsaw"
    private val weatherFromDb = Weather(1, cityName, listOf(Forecast(2, Date(), "", 0, 0, "")))
    private val weatherFromBackend = Weather(1, cityName, listOf(Forecast(2, Date(), "", 0, 0, "")))
    private val weatherTo = WeatherTo(CityTo(1, cityName, CoordinatesTo(0F,0F), ""), listOf(
        ForecastTo(1L, TemperatureTo(1F, 1F, 1F, 1F, 1F, 1F), 1F, 1, listOf(SummaryTo(1L, "", "", "")), 1F, 1, 1, 1F))
    )

    @Before
    fun setup() {
        weatherProvider = mock()
        weatherRepository = mock()
        apiMappers = mock()
        weatherInteractor = WeatherInteractor(weatherProvider, weatherRepository, apiMappers, Schedulers.trampoline())

        whenever(weatherRepository.add(weatherFromBackend)).thenReturn(Maybe.just(weatherFromBackend))
    }

    @Test
    fun `should return weather from db and backend api`() {
        whenever(weatherRepository.findByCityName(cityName)).thenReturn(Maybe.just(weatherFromDb))
        whenever(weatherProvider.getWeather(cityName)).thenReturn(Single.just(weatherTo))
        whenever(apiMappers.toDomainModel(weatherTo)).thenReturn(weatherFromBackend)
        weatherInteractor.getWeather(cityName).test()
            .assertResult(weatherFromDb, weatherFromBackend)
            .assertValueCount(2)
            .assertComplete()
    }

    @Test
    fun `should save weather to db`() {
        whenever(weatherRepository.findByCityName(cityName)).thenReturn(Maybe.just(weatherFromDb))
        whenever(weatherProvider.getWeather(cityName)).thenReturn(Single.just(weatherTo))
        whenever(apiMappers.toDomainModel(weatherTo)).thenReturn(weatherFromBackend)
        weatherInteractor.getWeather(cityName).test().assertComplete()
        verify(weatherRepository).add(weatherFromBackend)
    }

    @Test
    fun `should return weather form backend when db is empty`() {
        whenever(weatherRepository.findByCityName(cityName)).thenReturn(Maybe.empty())
        whenever(weatherProvider.getWeather(cityName)).thenReturn(Single.just(weatherTo))
        whenever(apiMappers.toDomainModel(weatherTo)).thenReturn(weatherFromBackend)
        weatherInteractor.getWeather(cityName).test()
            .assertResult(weatherFromBackend)
            .assertValueCount(1)
            .assertComplete()
    }

    @Test
    fun `should not return result when city name is invalid`() {
        val invalidCityName = "x"
        whenever(weatherRepository.findByCityName(invalidCityName)).thenReturn(Maybe.empty())
        whenever(weatherProvider.getWeather(invalidCityName)).thenReturn(Single.error(IllegalArgumentException()))
        whenever(apiMappers.toDomainModel(weatherTo)).thenThrow(IllegalArgumentException::class.java)
        weatherInteractor.getWeather(invalidCityName).test()
            .assertValueCount(0)
            .assertError(IllegalArgumentException::class.java)
    }

}