package pl.training.goodweather.configuration

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.training.goodweather.forecast.model.database.CityDb
import pl.training.goodweather.forecast.model.database.ForecastDb
import pl.training.goodweather.forecast.model.database.WeatherDao

@Database(entities = [CityDb::class, ForecastDb::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}