package pl.training.goodweather.forecast

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.training.goodweather.common.Logger
import pl.training.goodweather.configuration.ApplicationDatabase
import pl.training.goodweather.forecast.model.WeatherInteractor
import pl.training.goodweather.forecast.model.api.WeatherProvider
import pl.training.goodweather.forecast.model.database.InMemoryWeatherRepository
import pl.training.goodweather.forecast.model.database.RoomWeatherRepository
import pl.training.goodweather.forecast.model.database.WeatherDao
import pl.training.goodweather.forecast.model.database.WeatherRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ForecastModule {

    @Singleton
    @Provides
    fun weatherProvider(httpClient: OkHttpClient): WeatherProvider = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherProvider::class.java)

    @Singleton
    @Provides
    fun weatherDao(database: ApplicationDatabase): WeatherDao = database.weatherDao()

    @Named("roomRepository")
    @Singleton
    @Provides
    fun roomWeatherRepository(weatherDao: WeatherDao): WeatherRepository = RoomWeatherRepository(weatherDao)

    @Named("inMemoryRepository")
    @Singleton
    @Provides
    fun inMemoryWeatherRepository(): WeatherRepository = InMemoryWeatherRepository()

    @Singleton
    @Provides
    fun weatherInteractor(weatherProvider: WeatherProvider, @Named("inMemoryRepository") weatherRepository: WeatherRepository, logger: Logger) =
        WeatherInteractor(weatherProvider, weatherRepository, logger)

}