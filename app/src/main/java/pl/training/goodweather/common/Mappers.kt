package pl.training.goodweather.common

import java.text.SimpleDateFormat
import java.util.*

object Mappers {

    fun toShortDate(date: Date, format: String = "dd/MM"): String =
        SimpleDateFormat(format, Locale.getDefault()).format(date)

}