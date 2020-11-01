package pl.training.goodweather.forecast.model.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class CityDb(@PrimaryKey val id: Long, var name: String)

@Entity
data class ForecastDb(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    val date: Long,
    val description: String,
    val minTemperature: Int,
    val maxTemperature: Int,
    val iconUrl: String,
    val cityId: Long
)

data class WeatherDb(
    @Embedded val city: CityDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "cityId"
    ) val forecast: List<ForecastDb>
)