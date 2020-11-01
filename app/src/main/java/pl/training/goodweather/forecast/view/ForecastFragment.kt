package pl.training.goodweather.forecast.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
import pl.training.goodweather.forecast.model.Forecast
import pl.training.goodweather.forecast.presenter.ForecastPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_forecast.city_name as cityName
import kotlinx.android.synthetic.main.fragment_forecast.forecast_list as forecastList

class ForecastFragment : Fragment(), ForecastView {

    private val disposableBag = CompositeDisposable()

    @Inject
    lateinit var forecastPresenter: ForecastPresenter
    @Inject
    lateinit var logger: Logger

    private val forecastListAdapter = ForecastListAdapter(emptyList())

    init {
        applicationGraph.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastPresenter.attachView(this)
        initViews()
        bindViews()
    }

    private fun initViews() {
        forecastListAdapter.forecastTapListener = { forecastPresenter.onForecastSelected() }
        forecastList.layoutManager = LinearLayoutManager(activity)
        forecastList.adapter = forecastListAdapter
    }

    private fun bindViews() {
        cityName.textChanges()
            .map { it.toString().trim() }
            .filter { it.isNotEmpty() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(forecastPresenter::onCityChanged) { logger.log(it.toString()) }
            .addTo(disposableBag)
    }

    override fun showCityName(cityName: String) {
        this.cityName.setText(cityName)
    }

    override fun showForecast(forecast: List<Forecast>) {
       forecastListAdapter.update(forecast)
    }

    override fun showForecastDetails(cityName: String) {
        findNavController().navigate(R.id.action_show_forecast_details, bundleOf("cityName" to cityName))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposableBag.clear()
        forecastPresenter.detachView()
    }

}