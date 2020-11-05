package pl.training.goodweather

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import pl.training.goodweather.configuration.MIGRATION_1_2
import pl.training.goodweather.configuration.ApplicationDatabase

class WeatherDaoTest : DatabaseTest() {

    @get:Rule
    val migrationTestHelper = MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
        ApplicationDatabase::class.java.canonicalName, FrameworkSQLiteOpenHelperFactory())

    @Test
    fun should_not_return_forecast_when_db_is_empty() {
        database.weatherDao()
            .findByCityName(FakeDataProvider.cityName)
            .test()
            .assertValueCount(0)
            .assertComplete()
    }

    @Test
    fun should_replace_city_if_already_exists() {
        val weatherDao = database.weatherDao()
        weatherDao.add(FakeDataProvider.getCityDb())
        weatherDao.add(FakeDataProvider.getCityDb())
        weatherDao.findByCityName(FakeDataProvider.cityName)
            .test()
            .assertValues(FakeDataProvider.toWeatherDb(FakeDataProvider.getCityDb()))
            .assertComplete()
    }

    @Test
    fun test() {
        val databaseHelper = migrationTestHelper.createDatabase(databaseName, 1)
        val cityDb = FakeDataProvider.getCityDb()
        val values = ContentValues()
        values.put("id", cityDb.id)
        values.put("name", cityDb.name)
        databaseHelper.insert("CityDb", CONFLICT_REPLACE, values)
        migrationTestHelper.runMigrationsAndValidate(databaseName, 2, false, MIGRATION_1_2)
    }

}