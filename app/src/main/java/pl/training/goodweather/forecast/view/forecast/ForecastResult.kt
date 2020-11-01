package pl.training.goodweather.forecast.view.forecast

import pl.training.goodweather.forecast.model.Weather

sealed class ForecastResult {

    object Refreshing: ForecastResult()

    data class Refreshed(val weather: Weather): ForecastResult()

}