package pl.training.goodweather.forecast.model.api

import pl.training.goodweather.forecast.model.Forecast
import pl.training.goodweather.forecast.model.Weather
import java.util.*

class ApiMappers {

    fun toDomainModel(weatherTo: WeatherTo) = with(weatherTo) {
        Weather(city.id, city.name.toLowerCase(Locale.ROOT), list.map { toDomainModel(it) })
    }

    private fun toDomainModel(forecastTo: ForecastTo) = with(forecastTo) {
        Forecast(
            null,
            Date(forecastTo.dt * 1_000),
            weather[0].description,
            temp.min.toInt(),
            temp.max.toInt(),
            "https://openweathermap.org/img/wn/${weather[0].icon}.png"
        )
    }

}