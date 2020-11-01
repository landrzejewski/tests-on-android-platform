package pl.training.goodweather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.item_forecast.view.forecast_date as forecastDate
import kotlinx.android.synthetic.main.item_forecast.view.forecast_description as forecastDescription
import kotlinx.android.synthetic.main.item_forecast.view.forecast_icon as forecastIcon
import kotlinx.android.synthetic.main.item_forecast.view.forecast_max_temp as forecastMaxTemp
import kotlinx.android.synthetic.main.item_forecast.view.forecast_min_temp as forecastMinTemp

class ForecastListAdapter(var forecastList: List<ForecastTo>, var forecastTapListener: (ForecastTo) -> Unit = {})
    : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

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

    inner class ViewHolder(private val view: View, private val forecastTapListener: (ForecastTo) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindView(forecast: ForecastTo) = with(forecast) {
            view.setOnClickListener { forecastTapListener(this) }
            view.forecastDate.text = toDateString(dt * 1000)
            view.forecastDescription.text = weather[0].description
            view.forecastMinTemp.text = context?.getString(R.string.unit_symbol, temp.min.toInt())
            view.forecastMaxTemp.text = context?.getString(R.string.unit_symbol, temp.max.toInt())
            Picasso.get().load("https://openweathermap.org/img/wn/${weather[0].icon}.png").into(view.forecastIcon)
        }

    }

    private fun toDateString(mills: Long) = SimpleDateFormat("dd/MM", Locale.getDefault()).format(mills)

}