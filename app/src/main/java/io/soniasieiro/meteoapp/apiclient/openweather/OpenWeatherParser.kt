package io.soniasieiro.meteoapp.apiclient.openweather

import io.soniasieiro.meteoapp.commons.getDate
import io.soniasieiro.meteoapp.datamodels.Forecast
import io.soniasieiro.meteoapp.datamodels.ForecastHour
import kotlin.math.roundToInt

class OpenWeatherParser() {
        companion object {
            fun from(response: OpenWeatherResponse): Forecast {
                val maxHours = 12
                var currentHours = 0
                val hourForecast = response.hourly!!
                var hours = mutableListOf<ForecastHour>()

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

            fun parseHour(hour: HourlyItem?): ForecastHour {
                var parsedHour = getDate(hour?.dt?.toLong())
                var icon = hour?.weather?.get(0)?.icon
                return ForecastHour (
                        temperature = hour?.temp?.roundToInt() ?: 0,
                        hour = "${parsedHour}h",
                        icon = "http://openweathermap.org/img/wn/${icon}@2x.png"
                )
            }
        }


}