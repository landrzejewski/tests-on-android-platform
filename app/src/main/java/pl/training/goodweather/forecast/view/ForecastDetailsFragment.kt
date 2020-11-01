package pl.training.goodweather.forecast.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import pl.training.goodweather.R
import pl.training.goodweather.WeatherApplication.Companion.applicationGraph
import pl.training.goodweather.common.Logger
import pl.training.goodweather.forecast.model.WeatherInteractor
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_forecast_details.back_button as backButton
import kotlinx.android.synthetic.main.fragment_forecast_details.weather_description as weatherDescription

class ForecastDetailsFragment : Fragment() {

    private val disposableBag = CompositeDisposable()

    @Inject
    lateinit var weatherInteractor: WeatherInteractor
    @Inject
    lateinit var logger: Logger

    init {
        applicationGraph.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
    }

    private fun initViews() {
        weatherInteractor.getWeather()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ weatherDescription.text = it.cityName }) { logger.log(it.toString()) }
            .addTo(disposableBag)
    }

    private fun bindViews() {
        backButton.setOnClickListener { findNavController().navigate(R.id.action_show_forecast) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposableBag.clear()
    }

}