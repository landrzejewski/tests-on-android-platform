package  pl.training.goodweather

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherProvider {

    @GET("forecast/daily?cnt=10&units=metric&appid=b933866e6489f58987b2898c89f542b8")
    fun getForecast(@Query("q") city: String): Observable<WeatherTo>

}