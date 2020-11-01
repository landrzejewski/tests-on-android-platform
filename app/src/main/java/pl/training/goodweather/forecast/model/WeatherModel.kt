package pl.training.goodweather.forecast.model

import java.util.*

data class Weather(val id: Long, val cityName: String, val forecast: List<Forecast>)

data class Forecast(var id: Long?, val date: Date, val description: String, val minTemperature: Int, val maxTemperature: Int, val iconUrl: String)