package io.soniasieiro.meteoapp.apiclient

import io.soniasieiro.meteoapp.apiclient.openweather.OpenWeatherImpl

class ApiClientFactory {
    companion object {
        fun getInstance(): ApiService {
            return OpenWeatherImpl()
        }
    }
}