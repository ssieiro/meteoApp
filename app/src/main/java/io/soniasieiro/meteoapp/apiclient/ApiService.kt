package io.soniasieiro.meteoapp.apiclient

import io.soniasieiro.meteoapp.datamodels.Forecast

interface ApiService {
    fun getForecast(lat: String, lon: String): Forecast
}