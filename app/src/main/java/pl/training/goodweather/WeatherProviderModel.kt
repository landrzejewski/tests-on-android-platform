package pl.training.goodweather

data class WeatherTo(val city: CityTo, val list: List<ForecastTo>)

data class CityTo(val id: Long, val name: String, val coord: CoordinatesTo, val country: String)

data class CoordinatesTo(val lon: Float, val lat: Float)

data class ForecastTo(val dt: Long, val temp: TemperatureTo, val pressure: Float, val humidity: Int,
                      val weather: List<SummaryTo>, val speed: Float, val deg: Int, val clouds: Int, val rain: Float)

data class TemperatureTo(val day: Float, val min: Float, val max: Float, val night: Float, val eve: Float, val morn: Float)

data class SummaryTo(val id: Long, val main: String, val description: String, val icon: String)