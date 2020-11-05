package pl.training.goodweather

import org.junit.Test

class WeatherDaoTest : DatabaseTest() {

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

}