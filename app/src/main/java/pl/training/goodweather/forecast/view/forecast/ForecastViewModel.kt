package pl.training.goodweather.forecast.view.forecast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.launch
import pl.training.goodweather.common.Logger
import pl.training.goodweather.forecast.model.WeatherInteractor
import pl.training.goodweather.forecast.view.forecast.ForecastIntent.RefreshForecast
import pl.training.goodweather.forecast.view.forecast.ForecastResult.*

@ActivityRetainedScoped
class ForecastViewModel @ViewModelInject constructor(
    private val weatherInteractor: WeatherInteractor,
    private val logger: Logger
) : ViewModel() {

    private val disposableBag = CompositeDisposable()
    private val cache = BehaviorSubject.create<ForecastResult>()

    fun process(intents: Observable<ForecastIntent>): Observable<ForecastViewState> {
        intents.subscribe(this::process).addTo(disposableBag)
        return cache.scan(ForecastViewState(), this::reduce)
    }

    private fun process(intent: ForecastIntent) = viewModelScope.launch {
        when (intent) {
            is RefreshForecast -> {
                val weather = Observable.just(weatherInteractor.getWeather(intent.cityName))
                concatRefreshing(weather.map {
                    it.then(
                        ForecastResult::Refreshed,
                        ForecastResult::Error
                    )
                })
            }
        }
            .subscribe(cache::onNext) { logger.log("Refresh failed") }
    }

    private fun concatRefreshing(result: Observable<ForecastResult>) =
        Observable.concat(Observable.just(Refreshing), result)

    private fun reduce(state: ForecastViewState, result: ForecastResult): ForecastViewState =
        when (result) {
            is Refreshing -> state.copy(isRefreshing = true, exception = "")
            is Refreshed -> state.copy(
                isRefreshing = false,
                weather = result.weather,
                exception = ""
            )
            is Error -> state.copy(
                isRefreshing = false,
                weather = null,
                exception = result.exception.javaClass.name
            )
        }

}