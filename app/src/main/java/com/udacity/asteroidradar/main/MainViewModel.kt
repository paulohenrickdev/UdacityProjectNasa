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
import com.udacity.asteroidradar.api.TODAY_DATE
import kotlinx.coroutines.launch
import java.lang.Exception
import com.udacity.asteroidradar.api.asDomainModel
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.repository.AsteroideRespository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val _asteroidsRepository = AsteroideRespository(database)

    private val _header = MutableLiveData<PictureOfDay>()
    val header: LiveData<PictureOfDay>
        get() = _header

    private val _navigateAsteroid = MutableLiveData<Asteroid>()
    val navigateAsteroid: LiveData<Asteroid>
        get() = _navigateAsteroid

    init {
        viewModelScope.launch {
            try {
                getHeader()
                showAll()
                showSaved()
                showToday()
                _asteroidsRepository.refreshAsteroids()
            }catch (e: Exception) {
                e.printStackTrace()
                Log.i("ERROR", "Error on corroutine scope")

                //ERROR
            }
        }
    }


    var asteroids = _asteroidsRepository.asteroid

    suspend fun getHeader() {
        _header.value = Network.asteroidsService.getHeader(Constants.API_KEY)
        Log.i("apod", _header.value.toString())
    }

    fun showAll() {

    }

    fun showToday() {
        asteroids = _asteroidsRepository.asteroidToday
    }

    fun showSaved() {
        asteroids = _asteroidsRepository.asteroidAll
    }

    fun navigate(asteroid: Asteroid) {
        _navigateAsteroid.value = asteroid
    }

    fun navigateComplete() {
        _navigateAsteroid.value = null
    }

}