package pl.training.goodweather.forecast.view

import pl.training.goodweather.common.mvp.MvpView
import pl.training.goodweather.forecast.model.Weather

interface ForecastDetailsView : MvpView {

    fun showForecastDetails(weather: Weather)

    fun showForecast()

}