package pl.training.goodweather.forecast.view.forecastdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import pl.training.goodweather.R
import pl.training.goodweather.forecast.view.forecastdetails.ForecastDetailsViewState.*
import kotlinx.android.synthetic.main.fragment_forecast_details.back_button as backButton
import kotlinx.android.synthetic.main.fragment_forecast_details.weather_description as weatherDescription

@FragmentScoped
class ForecastDetailsFragment : Fragment() {

    private val disposableBag = CompositeDisposable()
    private val forecastDetailsViewModel: ForecastDetailsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        val cityName = arguments?.getString("cityName") ?: ""
        forecastDetailsViewModel.process(Observable.just(ForecastDetailsIntent.RefreshForecast(cityName)))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::render) { Log.d("###", it.toString()) }
            .addTo(disposableBag)
        backButton.setOnClickListener { findNavController().navigate(R.id.action_show_forecast) }
    }

    private fun render(viewState: ForecastDetailsViewState) {
        when (viewState) {
            is Initial -> weatherDescription.text = ""
            is Refreshing -> weatherDescription.text = getString(R.string.refreshing)
            is Refreshed -> weatherDescription.text = viewState.weather.cityName
        }
    }

}