package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.asDomainModel
import com.udacity.asteroidradar.repository.AsteroideRespository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val _header = MutableLiveData<PictureOfDay>()
    val header: LiveData<PictureOfDay>
        get() = _header

    init {
        viewModelScope.launch {
            try {
                getHeader()
            }catch (e: Exception) {
                Log.i("ERROR", "Error on corroutine scope")
            }
        }
    }

    private fun getHeader() {
//        _header.value = Network.asteroideService.getHeader(
//            key = "Eyp9Hob78QV5MC0LHTyw9h5j0JBsvhIeygxWmK6a"
//        )
    }

}