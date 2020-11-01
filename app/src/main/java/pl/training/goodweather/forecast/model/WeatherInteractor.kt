package pl.training.goodweather.forecast.model

import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.await
import pl.training.goodweather.common.Response
import pl.training.goodweather.common.Response.Failure
import pl.training.goodweather.common.Response.Success
import pl.training.goodweather.forecast.model.api.ApiMappers.toDomainModel
import pl.training.goodweather.forecast.model.api.WeatherProvider
import pl.training.goodweather.forecast.model.database.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherInteractor @Inject constructor(
    private val weatherProvider: WeatherProvider,
    private val weatherRepository: WeatherRepository
) {

    suspend fun getWeather(cityName: String): Response<Weather, WeatherGetException> = try {
        val cachedWeather = weatherRepository.findByCityName(cityName)
        val refreshedWeather = weatherProvider.getWeather(cityName)
            .map { toDomainModel(it) }
            .subscribeOn(Schedulers.io())
            .await()
        //   weatherRepository.add(refreshedWeather)
        Success(refreshedWeather)
    } catch (e: Exception) {
        Failure(WeatherGetException())
    }

}

class WeatherGetException