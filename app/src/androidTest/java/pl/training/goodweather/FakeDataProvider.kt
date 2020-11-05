package pl.training.goodweather

import pl.training.goodweather.forecast.model.database.CityDb
import pl.training.goodweather.forecast.model.database.ForecastDb
import pl.training.goodweather.forecast.model.database.WeatherDb

object FakeDataProvider {

    const val cityName = "warsaw"

    fun getCityDb(id: Long = 1, cityName: String = this.cityName) = CityDb(id, cityName)

    fun toWeatherDb(cityDb: CityDb, forecast: List<ForecastDb> = emptyList()) = WeatherDb(cityDb, forecast)

}