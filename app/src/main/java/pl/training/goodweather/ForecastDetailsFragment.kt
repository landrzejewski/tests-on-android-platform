package pl.training.goodweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_forecast_details.back_button as backButton
import kotlinx.android.synthetic.main.fragment_forecast_details.weather_description as weatherDescription

class ForecastDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
    }

    private fun initViews() {
        weatherDescription.text = arguments?.getString("cityName")
    }

    private fun bindViews() {
        backButton.setOnClickListener { findNavController().navigate(R.id.action_show_forecast) }
    }

}