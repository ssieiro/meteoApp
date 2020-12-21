package io.soniasieiro.meteoapp.UI.Map

import com.google.android.gms.maps.model.LatLng
import io.soniasieiro.meteoapp.datamodels.Forecast

interface MapViewModelDelegate {
    fun forecastObtained(forecast: Forecast, location: LatLng)
}