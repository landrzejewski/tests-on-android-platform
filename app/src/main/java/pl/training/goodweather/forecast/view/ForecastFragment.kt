package pl.training.goodweather.forecast.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import pl.training.goodweather.R
import pl.training.goodweather.WeatherApplication.Companion.applicationGraph
import pl.training.goodweather.common.Logger
import pl.training.goodweather.forecast.model.WeatherInteractor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_forecast.city_name as cityName
import kotlinx.android.synthetic.main.fragment_forecast.forecast_list as forecastList

class ForecastFragment : Fragment() {

    private val disposableBag = CompositeDisposable()

    @Inject
    lateinit var weatherInteractor: WeatherInteractor
    @Inject
    lateinit var logger: Logger

    private val forecastListAdapter = ForecastListAdapter(emptyList()) { showForecastDetails() }

    init {
        applicationGraph.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
    }

    private fun initViews() {
        forecastList.layoutManager = LinearLayoutManager(activity)
        forecastList.adapter = forecastListAdapter
    }

    private fun bindViews() {
        cityName.textChanges()
            .map { it.toString().trim() }
            .filter { it.isNotEmpty() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(weatherInteractor::refreshWeather) { logger.log(it.toString()) }
            .addTo(disposableBag)
        weatherInteractor.getWeather()
            .map { it.forecast }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(forecastListAdapter::update) { logger.log(it.toString()) }
            .addTo(disposableBag)
    }

    private fun showForecastDetails() {
        findNavController().navigate(R.id.action_show_forecast_details)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposableBag.clear()
    }

}