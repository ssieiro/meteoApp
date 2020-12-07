package io.soniasieiro.meteoapp

import io.soniasieiro.meteoapp.UI.Map.MapViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CustomViewModelFactory(private val application: Application) :
        ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MapViewModel::class.java) -> MapViewModel(application)
                else -> throw IllegalArgumentException("Unknown ViewModel $modelClass")
            }
        } as T
    }
}