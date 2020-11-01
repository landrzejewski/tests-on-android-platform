package pl.training.goodweather.forecast.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Maybe

@Dao
interface WeatherDao {

    @Insert(onConflict = REPLACE)
    fun add(cityDb: CityDb)

    @Insert
    fun add(forecastDb: List<ForecastDb>)

    @Transaction
    @Query("select * from CityDb where name = :cityName")
    fun findByCityName(cityName: String): Maybe<WeatherDb>

}