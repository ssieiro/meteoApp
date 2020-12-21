package io.soniasieiro.meteoapp.managers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.soniasieiro.meteoapp.R
import io.soniasieiro.meteoapp.commons.showSnackbar

class UserLocation {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    fun checkPermissions(activity: Activity): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(activity: Activity){
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        if (shouldProvideRationale) showSnackbar(activity, R.string.permission_rationale, android.R.string.ok) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation(activity: Activity, onSuccess: (Location) -> Unit) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient.lastLocation?.addOnSuccessListener {
            onSuccess(it)
        }
    }
}