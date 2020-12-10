package io.soniasieiro.meteoapp.UI.Map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.soniasieiro.meteoapp.R
import io.soniasieiro.meteoapp.data.AppModels.Forecast
import io.soniasieiro.meteoapp.data.AppModels.ForecastHour
import io.soniasieiro.meteoapp.network.UserLocation.Companion.REQUEST_PERMISSIONS_REQUEST_CODE
import kotlinx.android.synthetic.main.maps_activity.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback, MapViewModelDelegate {

    private lateinit var mMap: GoogleMap
    private var forecastHourList: List<ForecastHour>? = null
    private var forecastAdapter: ForecastAdapter? = null
    private var lastLocation: LatLng? = null
    private var mViewModel = MapViewModel(this@MapActivity)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps_activity)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()
    }

    override fun onStart() {
        super.onStart()
        mViewModel.askForLocationPermissions(this@MapActivity)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMapClickListener {
            this.lastLocation = it
            onMapClicked(it)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE && grantResults[0] == 0){
            mViewModel.getLocation(this@MapActivity)
        }
    }


    private fun init() {
        forecastList.layoutManager = LinearLayoutManager(this)

    }

    override fun forecastObtained(forecast: Forecast, location: LatLng) {
        forecastHourList = forecast.forecastByHours
        refresh()
        this.lastLocation = location
        lastLocation?.let {
           mMap.addMarker(MarkerOptions().position(it).title("myLocation"))
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 10.0f))
       }
    }

    fun refresh(){
        forecastAdapter = ForecastAdapter(applicationContext, forecastHourList)
        forecastList.adapter = forecastAdapter
        forecastAdapter!!.notifyDataSetChanged()
    }

    private fun onMapClicked(location: LatLng){
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(location).title("myLocation"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10.0f))
        mViewModel.getForecast(location)
    }
}