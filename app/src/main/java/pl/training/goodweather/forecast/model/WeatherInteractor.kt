package pl.training.goodweather.forecast.model

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import pl.training.goodweather.forecast.model.api.ApiMappers.toDomainModel
import pl.training.goodweather.forecast.model.api.WeatherProvider
import pl.training.goodweather.forecast.model.database.WeatherRepository

class WeatherInteractor(private val weatherProvider: WeatherProvider, private val weatherRepository: WeatherRepository) {

    fun getWeather(cityName: String): Observable<Weather> {
        val cachedWeather = weatherRepository.findByCityName(cityName)
            .toObservable()
        val refreshedWeather = weatherProvider.getWeather(cityName)
            .map { toDomainModel(it) }
            .toObservable()
            .flatMap { weatherRepository.add(it).toObservable() }
        return Observable.concat(cachedWeather, refreshedWeather)
            .subscribeOn(Schedulers.io())
    }

}