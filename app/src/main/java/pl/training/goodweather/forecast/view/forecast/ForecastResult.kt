package pl.training.goodweather.forecast.view.forecast

import pl.training.goodweather.forecast.model.Weather
import pl.training.goodweather.forecast.model.WeatherGetException

sealed class ForecastResult {

    object Refreshing : ForecastResult()

    data class Refreshed(val weather: Weather) : ForecastResult()

    data class Error(val exception: WeatherGetException) : ForecastResult()

}