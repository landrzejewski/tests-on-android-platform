package pl.training.goodweather

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import pl.training.goodweather.common.FakeDataProvider
import pl.training.goodweather.forecast.model.database.RoomWeatherRepository
import pl.training.goodweather.forecast.model.database.WeatherDao

class RoomWeatherRepositoryTest {

    private lateinit var weatherDao: WeatherDao
    private lateinit var weatherRepository: RoomWeatherRepository

    @Before
    fun setup() {
        weatherDao = mock()
        weatherRepository = RoomWeatherRepository(weatherDao, Schedulers.trampoline())
    }

    @Test
    fun test() {
        val cityDb = FakeDataProvider.getCityDb()
        val forecastDb = FakeDataProvider.getForecastList()
        val weather = FakeDataProvider.getWeather()
        weatherRepository.add(weather)
            .test()
            .assertValues(weather)
            .assertComplete()
        verify(weatherDao).add(cityDb)
        verify(weatherDao).add(forecastDb)
    }

}