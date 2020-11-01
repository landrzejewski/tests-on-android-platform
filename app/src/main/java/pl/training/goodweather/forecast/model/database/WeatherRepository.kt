package pl.training.goodweather.forecast.model.database

import io.reactivex.Maybe
import pl.training.goodweather.forecast.model.Weather

interface WeatherRepository {

    fun add(weather: Weather): Maybe<Weather>

    fun findByCityName(cityName: String): Maybe<Weather>

}