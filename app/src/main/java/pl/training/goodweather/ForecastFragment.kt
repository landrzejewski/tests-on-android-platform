package pl.training.goodweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.fragment_forecast.city_name as cityName
import kotlinx.android.synthetic.main.fragment_forecast.forecast_list as forecastList

class ForecastFragment : Fragment() {

    private val disposableBag = CompositeDisposable()
    private val httpClient = {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .build()
    }()
    private val weatherProvider = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherProvider::class.java)
    private val forecastListAdapter = ForecastListAdapter(emptyList(), this::showForecastDetails)
    private val weatherDao = InMemoryWeatherDao()

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
        val cityChanges = cityName.textChanges()
            .map { it.toString().trim() }
            .filter { it.isNotEmpty() }
            .debounce(500, TimeUnit.MILLISECONDS)
        disposableBag.add(cityChanges
            .flatMap { weatherDao.findByCityName(it).toObservable() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::showForecast)
        )
        disposableBag.add(cityChanges
            .flatMap(weatherProvider::getForecast)
            .flatMap { weatherDao.add(it).toObservable() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::showForecast) { showError("refreshFailed") }
        )
    }

    private fun showForecast(weather: WeatherTo) {
         forecastListAdapter.forecastList = weather.list
         forecastListAdapter.notifyDataSetChanged()
    }

    private fun showError(key: String) {
        val id = resources.getIdentifier(key, "string", activity?.packageName)
        Toast.makeText(activity, getString(id), Toast.LENGTH_LONG).show()
    }

    private fun showForecastDetails(forecast: ForecastTo) {
        findNavController().navigate(R.id.action_show_forecast_details, bundleOf("cityName" to cityName.text.toString()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposableBag.clear()
    }

}