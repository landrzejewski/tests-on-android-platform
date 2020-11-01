package pl.training.goodweather.forecast.model.database

import io.reactivex.Maybe
import pl.training.goodweather.forecast.model.Weather
import java.util.*

class InMemoryWeatherRepository: WeatherRepository {

    private val data = mutableMapOf<String, Weather>()

    override fun add(weather: Weather): Maybe<Weather> {
        val key = weather.cityName.toLowerCase(Locale.ROOT)
        data[key] = weather
        return Maybe.just(weather)
    }

    override fun findByCityName(cityName: String): Maybe<Weather> {
        val key = cityName.toLowerCase(Locale.ROOT)
        return if (data.containsKey(key)) {
            Maybe.just(data[key])
        } else {
            Maybe.empty()
        }
    }

}