package io.soniasieiro.meteoapp.datamodels

data class ForecastHour (
    val hour: String = "",
    val temperature: Int = 0,
    val icon: String = ""
)
