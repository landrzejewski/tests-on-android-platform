package pl.training.goodweather.forecast.view.forecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pl.training.goodweather.R
import pl.training.goodweather.common.Mappers
import pl.training.goodweather.forecast.model.Forecast
import kotlinx.android.synthetic.main.item_forecast.view.forecast_date as forecastDate
import kotlinx.android.synthetic.main.item_forecast.view.forecast_description as forecastDescription
import kotlinx.android.synthetic.main.item_forecast.view.forecast_icon as forecastIcon
import kotlinx.android.synthetic.main.item_forecast.view.forecast_max_temp as forecastMaxTemp
import kotlinx.android.synthetic.main.item_forecast.view.forecast_min_temp as forecastMinTemp

class ForecastListAdapter(
    private var forecastList: List<Forecast>,
    var forecastTapListener: (Forecast) -> Unit = {}
) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, forecastTapListener)
    }

    override fun getItemCount() = forecastList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(forecastList[position])
    }

    fun update(forecastList: List<Forecast>) {
        this.forecastList = forecastList
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val view: View,
        private val forecastTapListener: (Forecast) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bindView(forecast: Forecast) = with(forecast) {
            view.setOnClickListener { forecastTapListener(this) }
            view.forecastDate.text = Mappers.toShortDate(date)
            view.forecastDescription.text = description
            view.forecastMinTemp.text = context?.getString(R.string.unit_symbol, maxTemperature)
            view.forecastMaxTemp.text = context?.getString(R.string.unit_symbol, maxTemperature)
            Picasso.get().load(iconUrl).into(view.forecastIcon)
        }

    }

}