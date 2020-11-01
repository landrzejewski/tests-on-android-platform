package pl.training.goodweather.forecast.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import pl.training.goodweather.common.Logger
import pl.training.goodweather.forecast.model.Weather
import pl.training.goodweather.forecast.model.WeatherInteractor

@ActivityRetainedScoped
class ForecastViewModel @ViewModelInject constructor(private val weatherInteractor: WeatherInteractor, private val logger: Logger) : ViewModel() {

    private val disposableBag = CompositeDisposable()
    private val lastWeather = MutableLiveData<Weather>()

    private var lastNameCityName = ""

    fun refreshWeather(cityName: String) {
        if (cityName == lastNameCityName) { return }
        weatherInteractor.getWeather(cityName)
            .subscribe(this::onWeatherRefreshed) { logger.log(it.toString()) }
            .addTo(disposableBag)
    }

    private fun onWeatherRefreshed(weather: Weather) {
        lastNameCityName = weather.cityName
        lastWeather.postValue(weather)
    }

    fun getWeather(): LiveData<Weather> = lastWeather

    override fun onCleared() {
        super.onCleared()
        disposableBag.clear()
    }

}