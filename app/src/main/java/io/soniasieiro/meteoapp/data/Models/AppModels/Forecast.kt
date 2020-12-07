package io.soniasieiro.meteoapp.data.Models.AppModels

import io.soniasieiro.meteoapp.Utils
import io.soniasieiro.meteoapp.data.Models.ResponseModels.HourlyItem
import io.soniasieiro.meteoapp.data.Models.ResponseModels.WeatherResponse
import java.io.Serializable
import java.util.*
import kotlin.math.roundToInt

data class Forecast(
    val lat: String = "",
    val lon: String = "",
    val forecastByHours: List<HourForecast>? = null
): Serializable {
    companion object {
        var utils = Utils()
        fun parseForecast(response: WeatherResponse): Forecast {
            val maxHours = 12
            var currentHours = 0
            val hourForecast = response.hourly!!
            var hours = mutableListOf<HourForecast>()

            //add the first 13 elements

            for (hour in hourForecast){
                if (currentHours <= maxHours) {
                    val parsedHour = parseHour(hour)
                    hours.add(parsedHour)
                    currentHours +=1
                }
            }
            // delete first element to start the list in the current hour
            hours.removeAt(0)
            //sort the list by temperature
            hours.sortBy { it.temperature }

            return Forecast (
                lat = response.lat.toString(),
                lon = response.lon.toString(),
                forecastByHours = hours
            )
        }


        fun parseHour(hour: HourlyItem?): HourForecast {
            var parsedHour = utils.getDate(hour?.dt?.toLong())
            var icon = hour?.weather?.get(0)?.icon
            return HourForecast (
                temperature = hour?.temp?.roundToInt() ?: 0,
                hour = parsedHour,
                icon = "http://openweathermap.org/img/wn/${icon}@2x.png"
            )
        }
    }
}