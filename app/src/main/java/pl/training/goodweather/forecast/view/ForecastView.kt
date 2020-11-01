package pl.training.goodweather.forecast.view

import pl.training.goodweather.common.mvp.MvpView
import pl.training.goodweather.forecast.model.Forecast

interface ForecastView : MvpView {

    fun showCityName(cityName: String)

    fun showForecast(forecast: List<Forecast>)

    fun showForecastDetails(cityName: String)

}