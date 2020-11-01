package pl.training.goodweather

import android.app.Application

class WeatherApplication : Application() {

    companion object {

        lateinit var application: Application
            private set

    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

}