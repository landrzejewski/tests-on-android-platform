package pl.training.goodweather.configuration

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.training.goodweather.common.AndroidLogger
import pl.training.goodweather.common.Logger
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun logger(): Logger = AndroidLogger()

    @Singleton
    @Provides
    fun httpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): ApplicationDatabase = Room.databaseBuilder(context, ApplicationDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()

}