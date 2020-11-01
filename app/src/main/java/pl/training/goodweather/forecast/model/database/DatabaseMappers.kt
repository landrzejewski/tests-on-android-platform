package pl.training.goodweather.forecast.model.database

import pl.training.goodweather.forecast.model.Forecast
import pl.training.goodweather.forecast.model.Weather
import java.util.*

object DatabaseMappers {

    fun toDomainModel(weatherDb: WeatherDb) = with(weatherDb) {
        Weather(city.id, city.name.toLowerCase(Locale.ROOT), forecast.map { toDomainModel(it) })
    }

    private fun toDomainModel(forecastDb: ForecastDb) = with(forecastDb) {
        Forecast(id, Date(date), description, minTemperature, maxTemperature, iconUrl)
    }

    fun toDatabaseModel(weather: Weather) = with(weather) {
        CityDb(id, cityName.toLowerCase(Locale.ROOT))
    }

    fun toDatabaseModel(forecast: Forecast, cityId: Long) = with(forecast) {
        ForecastDb(null, date.time, description, minTemperature, maxTemperature, iconUrl, cityId)
    }

}