package pl.training.goodweather.forecast.view.forecastdetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Observable
import pl.training.goodweather.forecast.model.WeatherInteractor
import pl.training.goodweather.forecast.view.forecastdetails.ForecastDetailsIntent.*
import pl.training.goodweather.forecast.view.forecastdetails.ForecastDetailsResult.*

@ActivityRetainedScoped
class ForecastDetailsViewModel @ViewModelInject constructor(private val weatherInteractor: WeatherInteractor) : ViewModel() {

    fun process(intents: Observable<ForecastDetailsIntent>): Observable<ForecastDetailsViewState> = intents.flatMap { intent ->
        when(intent) {
            is RefreshForecast -> concatRefreshing(weatherInteractor.getWeather(intent.cityName).map { Refreshed(it) } )
        }
        .scan(ForecastDetailsViewState.Initial, this::reduce)
    }

    private fun reduce(state: ForecastDetailsViewState, result: ForecastDetailsResult): ForecastDetailsViewState = when (result) {
        is Refreshing -> ForecastDetailsViewState.Refreshing
        is Refreshed -> ForecastDetailsViewState.Refreshed(result.weather)
    }

    private fun concatRefreshing(result: Observable<ForecastDetailsResult>) = Observable.concat(Observable.just(Refreshing), result)

}