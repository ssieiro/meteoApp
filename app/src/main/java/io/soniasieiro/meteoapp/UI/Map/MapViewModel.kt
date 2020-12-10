package io.soniasieiro.meteoapp.UI.Map

import android.Manifest
import android.content.Context
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.google.android.gms.maps.model.LatLng
import io.soniasieiro.meteoapp.network.MeteoAppService
import io.soniasieiro.meteoapp.network.UserLocation
import io.soniasieiro.meteoapp.network.UserLocation.Companion.REQUEST_PERMISSIONS_REQUEST_CODE

class MapViewModel(private val context: Context) {

    private var mapViewModelDelegate: MapViewModelDelegate? = null
    private lateinit var location: LatLng
    var apiService = MeteoAppService()
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