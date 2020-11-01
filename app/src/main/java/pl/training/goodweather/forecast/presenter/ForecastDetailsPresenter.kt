package pl.training.goodweather.forecast.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import pl.training.goodweather.common.Logger
import pl.training.goodweather.common.mvp.BasePresenter
import pl.training.goodweather.forecast.model.WeatherInteractor
import pl.training.goodweather.forecast.view.ForecastDetailsView

class ForecastDetailsPresenter(private  val weatherInteractor: WeatherInteractor, private val logger: Logger) : BasePresenter<ForecastDetailsView>() {

    fun onCityChanged(cityName: String) {
        weatherInteractor.getWeather(cityName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ view?.showForecastDetails(it) }, { logger.log(it.toString()) })
            .addTo(disposableBag)
    }

    fun onClose() {
        view?.showForecast()
    }

}