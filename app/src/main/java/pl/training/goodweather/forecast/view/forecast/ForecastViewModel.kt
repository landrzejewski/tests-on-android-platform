package pl.training.goodweather.forecast.view.forecast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import pl.training.goodweather.common.Logger
import pl.training.goodweather.forecast.model.WeatherInteractor
import pl.training.goodweather.forecast.view.forecast.ForecastIntent.RefreshForecast
import pl.training.goodweather.forecast.view.forecast.ForecastResult.Refreshed
import pl.training.goodweather.forecast.view.forecast.ForecastResult.Refreshing

@ActivityRetainedScoped
class ForecastViewModel @ViewModelInject constructor(private val weatherInteractor: WeatherInteractor, private val logger: Logger) : ViewModel() {

    private val disposableBag = CompositeDisposable()
    private val cache = BehaviorSubject.create<ForecastResult>()

    fun process(intents: Observable<ForecastIntent>): Observable<ForecastViewState> {
        intents.subscribe { intent ->
            when (intent) {
                is RefreshForecast -> concatRefreshing(weatherInteractor.getWeather(intent.cityName).map { Refreshed(it) })
            }.subscribe(cache::onNext) { logger.log("Refresh failed") }
        }
        .addTo(disposableBag)
        return cache.scan(ForecastViewState(true, null), this::reduce)
    }

    private fun reduce(state: ForecastViewState, result: ForecastResult): ForecastViewState = when (result) {
        is Refreshing -> state.copy(isRefreshing = true)
        is Refreshed -> state.copy(isRefreshing = false, weather = result.weather)
    }

    private fun concatRefreshing(result: Observable<ForecastResult>) = Observable.concat(Observable.just(Refreshing), result)

}