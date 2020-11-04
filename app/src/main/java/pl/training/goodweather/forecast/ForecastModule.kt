package pl.training.goodweather.forecast

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import pl.training.goodweather.configuration.ApplicationDatabase
import pl.training.goodweather.forecast.model.api.ApiMappers
import pl.training.goodweather.forecast.model.api.WeatherProvider
import pl.training.goodweather.forecast.model.database.InMemoryWeatherRepository
import pl.training.goodweather.forecast.model.database.RoomWeatherRepository
import pl.training.goodweather.forecast.model.database.WeatherDao
import pl.training.goodweather.forecast.model.database.WeatherRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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

    @RoomRepository
    @Singleton
    @Provides
    fun roomWeatherRepository(weatherDao: WeatherDao, scheduler: Scheduler): WeatherRepository = RoomWeatherRepository(weatherDao, scheduler)

    @InMemoryRepository
    @Singleton
    @Provides
    fun inMemoryWeatherRepository(): WeatherRepository = InMemoryWeatherRepository()

    @Singleton
    @Provides
    fun scheduler() = Schedulers.io()

    @Singleton
    @Provides
    fun apiMappers() = ApiMappers()

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RoomRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InMemoryRepository