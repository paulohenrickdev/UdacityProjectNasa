package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.NetworkPod
import kotlinx.coroutines.launch
import java.lang.Exception
import com.udacity.asteroidradar.api.asDomainModel
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroideRespository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _asteroidsRepository = AsteroideRespository(getDatabase(application))


    private val _header = MutableLiveData<PictureOfDay>()
    val header: LiveData<PictureOfDay>
        get() = _header

    init {
        viewModelScope.launch {
            try {
                getHeader()
                _asteroidsRepository.refreshAsteroids()
            }catch (e: Exception) {
                e.printStackTrace()
                Log.i("ERROR", "Error on corroutine scope")

                //ERROR
            }
        }
    }

    val asteroids = _asteroidsRepository.asteroid

    suspend fun getHeader() {
        _header.value = Network.asteroidsService.getHeader(Constants.API_KEY)
        Log.i("apod", _header.value.toString())
    }

}