package pl.training.goodweather.forecast.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.disposables.CompositeDisposable
import pl.training.goodweather.R
import pl.training.goodweather.WeatherApplication.Companion.applicationGraph
import pl.training.goodweather.forecast.model.Weather
import pl.training.goodweather.forecast.presenter.ForecastDetailsPresenter
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_forecast_details.back_button as backButton
import kotlinx.android.synthetic.main.fragment_forecast_details.weather_description as weatherDescription

class ForecastDetailsFragment : Fragment(), ForecastDetailsView {

    private val disposableBag = CompositeDisposable()

    @Inject
    lateinit var forecastDetailsPresenter: ForecastDetailsPresenter

    init {
        applicationGraph.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applicationGraph.inject(this)
        forecastDetailsPresenter.attachView(this)
        initViews()
        bindViews()
    }

    private fun initViews() {
        arguments?.getString("cityName")?.let { forecastDetailsPresenter.onCityChanged(it) }
    }

    private fun bindViews() {
        backButton.setOnClickListener { forecastDetailsPresenter.onClose() }
    }

    override fun showForecastDetails(weather: Weather) {
        weatherDescription.text = weather.cityName
    }

    override fun showForecast() {
        findNavController().navigate(R.id.action_show_forecast)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposableBag.clear()
        forecastDetailsPresenter.detachView()
    }

}