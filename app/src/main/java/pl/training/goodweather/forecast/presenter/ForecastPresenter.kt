package pl.training.goodweather.forecast.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import pl.training.goodweather.common.Logger
import pl.training.goodweather.common.mvp.BasePresenter
import pl.training.goodweather.forecast.model.Weather
import pl.training.goodweather.forecast.model.WeatherInteractor
import pl.training.goodweather.forecast.view.ForecastView

class ForecastPresenter(private val weatherInteractor: WeatherInteractor, private val logger: Logger) : BasePresenter<ForecastView>() {

    private var weather: Weather? = null

    override fun onViewAttached() {
        weather?.let {
            view?.showCityName(it.cityName)
            view?.showForecast(it.forecast)
        }
    }

    fun onForecastSelected() {
        weather?.let { view?.showForecastDetails(it.cityName) }
    }

    fun onCityChanged(cityName: String) {
        if (cityName == weather?.cityName) {
            return
        }
        weatherInteractor.getWeather(cityName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onForecastLoaded) { logger.log(it.toString()) }
            .addTo(disposableBag)
    }

    private fun onForecastLoaded(weather: Weather) {
        this.weather = weather
        view?.showForecast(weather.forecast)
    }

}