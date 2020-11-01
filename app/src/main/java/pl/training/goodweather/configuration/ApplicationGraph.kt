package pl.training.goodweather.configuration

import dagger.Component
import pl.training.goodweather.forecast.ForecastModule
import pl.training.goodweather.forecast.view.ForecastDetailsFragment
import pl.training.goodweather.forecast.view.ForecastFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ForecastModule::class])
interface ApplicationGraph {

    fun inject(forecastFragment: ForecastFragment)

    fun inject(forecastDetailsFragment: ForecastDetailsFragment)

}