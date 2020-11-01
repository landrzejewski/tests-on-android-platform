package  pl.training.goodweather.forecast.model.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherProvider {

    @GET("forecast/daily?cnt=10&units=metric&appid=b933866e6489f58987b2898c89f542b8")
    fun getWeather(@Query("q") city: String): Single<WeatherTo>

}