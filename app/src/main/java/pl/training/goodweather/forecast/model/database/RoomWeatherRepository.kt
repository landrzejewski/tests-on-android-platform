package pl.training.goodweather.forecast.model.database

import pl.training.goodweather.forecast.model.Weather
import pl.training.goodweather.forecast.model.database.DatabaseMappers.toDatabaseModel
import pl.training.goodweather.forecast.model.database.DatabaseMappers.toDomainModel

class RoomWeatherRepository(private val weatherDao: WeatherDao) : WeatherRepository {

    override suspend fun add(weather: Weather): Weather {
        val cityDb = toDatabaseModel(weather)
        weatherDao.add(cityDb)
        weatherDao.add(weather.forecast.map { toDatabaseModel(it, weather.id) })
        return weather
    }

    override suspend fun findByCityName(cityName: String): Weather? {
        return weatherDao.findByCityName(cityName)?.let(DatabaseMappers::toDomainModel)
    }

}