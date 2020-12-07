package io.soniasieiro.meteoapp.UI.Map

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.soniasieiro.meteoapp.R
import io.soniasieiro.meteoapp.data.Models.AppModels.ForecastHour
import kotlinx.android.synthetic.main.item_forecast_hour.view.*

class ForecastAdapter(private val context: Context, private val forecastHourList: List<ForecastHour>?): RecyclerView.Adapter<ForecastAdapter.ForecastHolder>() {

    override fun getItemCount(): Int {
        return forecastHourList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast_hour, parent, false)
        return ForecastHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        forecastHourList?.get(position).let { hour ->
            holder.hour = hour
        }

    }

    inner class ForecastHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hour: ForecastHour? = null
            set(value) {
                field = value
                itemView.tag = field

                field?.let {
                    itemView.textDegrees.text = "${it.temperature}º"
                    itemView.textHour.text = it.hour
                    Glide.with(context)
                            .load(it.icon)
                            .into(itemView.weatherIcon)
                }
            }

    }

}