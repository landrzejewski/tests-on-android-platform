package pl.training.goodweather

import io.reactivex.Maybe
import java.util.*

class InMemoryWeatherDao {

    private val data = mutableMapOf<String, WeatherTo>()

    fun add(weather: WeatherTo): Maybe<WeatherTo> {
        val key = weather.city.name.toLowerCase(Locale.ROOT)
        data[key] = weather
        return Maybe.just(weather)
    }

    fun findByCityName(cityName: String): Maybe<WeatherTo> {
        val key = cityName.toLowerCase(Locale.ROOT)
        return if (data.containsKey(key)) {
            Maybe.just(data[cityName.toLowerCase(Locale.ROOT)])
        } else {
            Maybe.empty()
        }
    }

}