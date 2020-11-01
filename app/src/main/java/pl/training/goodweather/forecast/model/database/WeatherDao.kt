package pl.training.goodweather.forecast.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface WeatherDao {

    @Insert(onConflict = REPLACE)
    suspend fun add(cityDb: CityDb)

    @Insert
    suspend fun add(forecastDb: List<ForecastDb>)

    @Transaction
    @Query("select * from CityDb where name = :cityName")
    suspend fun findByCityName(cityName: String): WeatherDb?

}