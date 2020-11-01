package pl.training.goodweather.forecast.view.forecastdetails

import pl.training.goodweather.forecast.model.Weather

sealed class ForecastDetailsResult {

    object Refreshing: ForecastDetailsResult()

    data class Refreshed(val weather: Weather): ForecastDetailsResult()

}