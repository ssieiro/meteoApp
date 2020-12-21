package io.soniasieiro.meteoapp.UI.Map

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import io.soniasieiro.meteoapp.apiclient.ApiClientFactory
import io.soniasieiro.meteoapp.apiclient.ApiService
import io.soniasieiro.meteoapp.apiclient.openweather.OpenWeatherImpl
import io.soniasieiro.meteoapp.managers.UserLocation

class MapViewModel(private val context: Context) {

    private var mapViewModelDelegate: MapViewModelDelegate? = null
    private lateinit var location: LatLng
    var apiService: ApiService = ApiClientFactory.getInstance()
    val managerUserLocation = UserLocation()

    init {

        if (context is MapViewModelDelegate)
            mapViewModelDelegate = context
        else
            throw IllegalArgumentException("Context doesn't implement ${MapViewModelDelegate::class.java.canonicalName}")
    }

    fun askForLocationPermissions(activity: MapActivity) {
        if (managerUserLocation.checkPermissions(activity)) {
                getLocation(activity)
        } else {
            managerUserLocation.requestPermissions(activity)
        }
    }

    fun getLocation(activity: MapActivity) {
        managerUserLocation.getLocation(activity) { location ->
            this.location = LatLng(location.latitude, location.longitude)
            getForecast(this.location)}
    }

    fun getForecast(location: LatLng) {
        val forecast = apiService.getForecast(location.latitude.toString(), location.longitude.toString())
        mapViewModelDelegate?.forecastObtained(forecast, location)
    }



}