package pl.training.goodweather.forecast.view.forecastdetails

sealed class ForecastDetailsIntent {

    data class RefreshForecast(val cityName: String): ForecastDetailsIntent()

}