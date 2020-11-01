package pl.training.goodweather.configuration

import androidx.room.Database
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.training.goodweather.WeatherApplication
import pl.training.goodweather.WeatherApplication.Companion.application
import pl.training.goodweather.common.AndroidLogger
import pl.training.goodweather.common.Logger
import pl.training.goodweather.forecast.model.database.InMemoryWeatherRepository
import pl.training.goodweather.forecast.model.WeatherInteractor
import pl.training.goodweather.forecast.model.api.WeatherProvider
import pl.training.goodweather.forecast.model.database.RoomWeatherRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Container {

    fun getWeatherInteractor() = weatherInteractor

    fun getLogger() = logger

    private val logger: Logger = AndroidLogger()

    private val httpClient: OkHttpClient = {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .build()
    }()

    private val weatherProvider = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherProvider::class.java)

    private val inMemoryWeatherRepository = InMemoryWeatherRepository()

    private val database = Room.databaseBuilder(application, ApplicationDatabase::class.java, "database")
        .fallbackToDestructiveMigration()
        .build()

    private val roomWeatherRepository = RoomWeatherRepository(database.weatherDao())

    private val weatherInteractor = WeatherInteractor(weatherProvider, roomWeatherRepository, logger)

}