package io.soniasieiro.meteoapp.datamodels

import java.io.Serializable

data class Forecast(
    val lat: String = "",
    val lon: String = "",
    val forecastByHours: List<ForecastHour>? = null
): Serializable