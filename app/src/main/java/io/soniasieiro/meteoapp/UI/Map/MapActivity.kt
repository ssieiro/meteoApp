package io.soniasieiro.meteoapp.UI.Map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.soniasieiro.meteoapp.R
import io.soniasieiro.meteoapp.data.AppModels.Forecast
import io.soniasieiro.meteoapp.data.AppModels.ForecastHour
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMapClickListener {
            this.lastLocation = it
            onMapClicked(it)
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