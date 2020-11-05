package pl.training.goodweather.common

import pl.training.goodweather.forecast.model.Forecast
import pl.training.goodweather.forecast.model.Weather
import pl.training.goodweather.forecast.model.database.CityDb
import pl.training.goodweather.forecast.model.database.ForecastDb
import pl.training.goodweather.forecast.model.database.WeatherDb
import java.util.*

object FakeDataProvider {

    const val cityName = "warsaw"
    val date = Date()

    fun getCityDb(id: Long = 1, cityName: String = this.cityName) = CityDb(id, cityName)

    fun toWeatherDb(cityDb: CityDb, forecast: List<ForecastDb> = emptyList()) = WeatherDb(cityDb, forecast)

    fun getWeather(id: Long = 1, cityName: String = this.cityName, forecast: List<Forecast> = listOf(getForecast()))
            = Weather(id, cityName, forecast)

    fun getForecast() = Forecast(1, date, "cloudy", 3, 10, "")

    fun toForecastDb(forecast: Forecast, cityId: Long) = with(forecast) {
        ForecastDb(null, date.time, description, minTemperature, maxTemperature, iconUrl, cityId)
    }

}