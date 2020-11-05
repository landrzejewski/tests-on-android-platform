package pl.training.goodweather.configuration

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import pl.training.goodweather.forecast.model.database.CityDb
import pl.training.goodweather.forecast.model.database.ForecastDb
import pl.training.goodweather.forecast.model.database.WeatherDao

@Database(entities = [CityDb::class, ForecastDb::class], version = 2, exportSchema = true)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}

object MIGRATION_1_2: Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
       database.execSQL("ALTER TABLE CityDb ADD COLUMN lastUpdate INTEGER")
    }

}