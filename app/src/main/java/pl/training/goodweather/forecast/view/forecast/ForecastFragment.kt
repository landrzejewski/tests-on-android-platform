package pl.training.goodweather.forecast.view.forecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.textChanges
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import pl.training.goodweather.R
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.fragment_forecast.city_name as cityName
import kotlinx.android.synthetic.main.fragment_forecast.forecast_list as forecastList

@FragmentScoped
class ForecastFragment : Fragment() {

    private val disposableBag = CompositeDisposable()
    private val forecastViewModel: ForecastViewModel by activityViewModels()
    private val forecastListAdapter = ForecastListAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
    }

    private fun initViews() {
        forecastListAdapter.forecastTapListener = { showForecastDetails() }
        forecastList.layoutManager = LinearLayoutManager(activity)
        forecastList.adapter = forecastListAdapter
    }

    private fun bindViews() {
        forecastViewModel.process(Observable.merge(listOf(
            cityName.textChanges()
                .map { it.toString().trim() }
                .filter { it.isNotEmpty() }
                .debounce(500, TimeUnit.MILLISECONDS)
                .map { ForecastIntent.RefreshForecast(it) })
            // additional intents sources
        ))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::render) { Log.d("###", it.toString()) }
        .addTo(disposableBag)
    }

    private fun render(viewState: ForecastViewState) {
        forecastListAdapter.update(viewState.weather?.forecast ?: emptyList())
        if (viewState.exception.isNotEmpty()) {
            Toast.makeText(context, viewState.exception, Toast.LENGTH_LONG).show()
        }
    }

    private fun showForecastDetails() {
        findNavController().navigate(
            R.id.action_show_forecast_details,
            bundleOf("cityName" to cityName.text.toString())
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposableBag.clear()
    }

}