package pl.training.goodweather.forecast.model

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import pl.training.goodweather.common.Logger
import pl.training.goodweather.forecast.model.api.ApiMappers.toDomainModel
import pl.training.goodweather.forecast.model.api.WeatherProvider
import pl.training.goodweather.forecast.model.database.WeatherRepository

class WeatherInteractor(private val weatherProvider: WeatherProvider, private val weatherRepository: WeatherRepository, private val logger: Logger) {

    private val disposableBag = CompositeDisposable()
    private val currentWeather = BehaviorSubject.create<Weather>()

    fun refreshWeather(cityName: String): Completable {
        val cachedWeather = weatherRepository.findByCityName(cityName)
            .toObservable()
        val refreshedWeather = weatherProvider.getWeather(cityName)
            .map { toDomainModel(it) }
            .toObservable()
            .flatMap { weatherRepository.add(it).toObservable() }
        val weather = Observable.concat(cachedWeather, refreshedWeather)
            .share()
            .subscribeOn(Schedulers.io())
        weather
            .subscribe(currentWeather::onNext) { logger.log(it.toString()) }
            .addTo(disposableBag)
        return Completable.fromObservable(weather)
    }

    fun getWeather(): Observable<Weather> = currentWeather

}