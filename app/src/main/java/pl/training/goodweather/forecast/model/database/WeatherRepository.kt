package pl.training.goodweather.forecast.model.database

import pl.training.goodweather.forecast.model.Weather

interface WeatherRepository {

    suspend fun add(weather: Weather): Weather

    suspend fun findByCityName(cityName: String): Weather?

}