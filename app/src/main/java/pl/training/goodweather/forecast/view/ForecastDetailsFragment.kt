package pl.training.goodweather.forecast.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.scopes.FragmentScoped
import pl.training.goodweather.R
import pl.training.goodweather.forecast.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_forecast_details.back_button as backButton
import kotlinx.android.synthetic.main.fragment_forecast_details.weather_description as weatherDescription

@FragmentScoped
class ForecastDetailsFragment : Fragment() {

    private val forecastViewModel: ForecastViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        forecastViewModel.getWeather().observe(viewLifecycleOwner) { weatherDescription.text = it.cityName }
        backButton.setOnClickListener { findNavController().navigate(R.id.action_show_forecast) }
    }

}