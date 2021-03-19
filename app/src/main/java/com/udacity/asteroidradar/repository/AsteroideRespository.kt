package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.squareup.moshi.Json
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.TODAY_DATE
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.NasaDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroideRespository(private val database: NasaDatabase) {

    val asteroid: LiveData<List<Asteroid>> = Transformations.map(database.nasaDao.getAsteroides(TODAY_DATE)) {
        it.asDomainModel()
    }

    val asteroidToday: LiveData<List<Asteroid>> = Transformations.map(database.nasaDao.getAsteroidesToday(TODAY_DATE)) {
        it.asDomainModel()
    }

    val asteroidAll: LiveData<List<Asteroid>> = Transformations.map(database.nasaDao.getAllAsteroids()) {
        it.asDomainModel()
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids = parseAsteroidsJsonResult(
                JSONObject(
                    Network.asteroidsService.getAsteroids(TODAY_DATE, Constants.API_KEY)
                )
            )
            database.nasaDao.insertAll(*asteroids.asDatabaseModel())
        }
    }

}
