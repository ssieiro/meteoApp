package io.soniasieiro.meteoapp.UI.Map

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import io.soniasieiro.meteoapp.network.MeteoAppService
import io.soniasieiro.meteoapp.network.UserLocation

class MapViewModel(private val context: Context) {

    private var mapViewModelDelegate: MapViewModelDelegate? = null
    private lateinit var location: LatLng
    var apiService = MeteoAppService()

    init {

        if (context is MapViewModelDelegate)
            mapViewModelDelegate = context
        else
            throw IllegalArgumentException("Context doesn't implement ${MapViewModelDelegate::class.java.canonicalName}")
    }

    fun askForLocationPermissions(activity: MapActivity) {
        val managerUserLocation = UserLocation()
        if (managerUserLocation.checkPermissions(activity)) {
            managerUserLocation.getLocation(activity) { location ->
                this.location = LatLng(location.latitude, location.longitude)
                getForecast(this.location)
            }

        } else {
            managerUserLocation.requestPermissions(activity)
            // refactor to capture when the user gives the permissions
            askForLocationPermissions(activity)
        }
    }

    fun getForecast(location: LatLng) {
        val forecast = apiService.getForecast(location.latitude.toString(), location.longitude.toString())
        mapViewModelDelegate?.forecastObtained(forecast, location)
    }



}