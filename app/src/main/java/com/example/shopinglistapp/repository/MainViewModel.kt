package com.example.shopinglistapp.repository

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopinglistapp.interfaces_Objects.RetrofitClient
import com.example.shopinglistapp.model.GeocodingResult
import com.example.shopinglistapp.model.Location
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _location = mutableStateOf<Location?>(null)
    val location = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    fun updateLocation(newLocation: Location){
        _location.value = newLocation
    }

    fun fetchAddress(latlng: String){
        try{
            viewModelScope.launch {
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng,
                    "AIzaSyCnfwyKH4V8CE5XR38GGg_uZysFyHIXeZU"
                )
                _address.value = result.results
            }
        }catch(e:Exception) {
            Log.d("res1", "${e.cause} ${e.message}")
        }
    }
}