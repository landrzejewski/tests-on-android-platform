package pl.training.goodweather.forecast.view.forecast

sealed class ForecastIntent {

    data class RefreshForecast(val cityName: String) : ForecastIntent()

}