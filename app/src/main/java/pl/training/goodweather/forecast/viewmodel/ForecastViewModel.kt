package pl.training.goodweather.forecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import pl.training.goodweather.WeatherApplication.Companion.applicationGraph
import pl.training.goodweather.common.Logger
import pl.training.goodweather.forecast.model.Weather
import pl.training.goodweather.forecast.model.WeatherInteractor
import javax.inject.Inject

class ForecastViewModel : ViewModel() {

    private val disposableBag = CompositeDisposable()
    private val lastWeather = MutableLiveData<Weather>()

    private var lastNameCityName = ""

    //private lateinit var  weatherInteractor: WeatherInteractor
    @Inject
    lateinit var  weatherInteractor: WeatherInteractor
    @Inject
    lateinit var  logger: Logger

    init {
        applicationGraph.inject(this)
    }

    //    @Inject
    //    fun setWeatherInteractor(weatherInteractor: WeatherInteractor) {
    //        this.weatherInteractor = weatherInteractor
    //    }

    fun refreshWeather(cityName: String) {
        // LiveDataReactiveStreams.fromPublisher()
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