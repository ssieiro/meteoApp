package io.soniasieiro.meteoapp.UI.Map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.soniasieiro.meteoapp.CustomViewModelFactory
import io.soniasieiro.meteoapp.R
import io.soniasieiro.meteoapp.data.LAT
import io.soniasieiro.meteoapp.data.LON
import io.soniasieiro.meteoapp.data.MeteoAppService
import io.soniasieiro.meteoapp.data.Models.AppModels.ForecastHour
import kotlinx.android.synthetic.main.maps_activity.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var apiService = MeteoAppService()
    private var forecastHourList: List<ForecastHour>? = null
    private var forecastAdapter: ForecastAdapter? = null

    private val mViewModel: MapViewModel by lazy {
        val factory = CustomViewModelFactory(application)
        ViewModelProvider(this, factory).get(MapViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps_activity)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()
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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun init() {
        forecastList.layoutManager = LinearLayoutManager(this)
        val forecast = apiService.getForecast(LAT, LON)
        forecastHourList = forecast.forecastByHours
        forecastAdapter = ForecastAdapter(applicationContext, forecastHourList)
        forecastList.adapter = forecastAdapter



    }
}