package pl.training.goodweather.forecast.view.forecastdetails

import pl.training.goodweather.forecast.model.Weather

sealed class ForecastDetailsViewState {

    object Initial : ForecastDetailsViewState()

    object Refreshing : ForecastDetailsViewState()

    class Refreshed(val weather: Weather) : ForecastDetailsViewState()

}