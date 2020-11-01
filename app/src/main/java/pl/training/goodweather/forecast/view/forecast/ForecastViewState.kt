package pl.training.goodweather.forecast.view.forecast

import pl.training.goodweather.forecast.model.Weather

data class ForecastViewState(
    val isRefreshing: Boolean = false,
    val weather: Weather? = null,
    val exception: String = ""
)
