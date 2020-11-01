package pl.training.goodweather.common

import android.util.Log

class AndroidLogger : Logger {

    private val tag = AndroidLogger::class.java.name

    override fun log(text: String) {
        Log.d(tag, text);
    }

}