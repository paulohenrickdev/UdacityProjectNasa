package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.TODAY_DATE
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.database.NasaDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AsteroideRespository(private val database: NasaDatabase) {

    val asteroid: LiveData<List<Asteroid>> = Transformations.map(database.nasaDao.getAsteroides()) {
        it.asDomainModel()
    }

    suspend fun refreshAsteroides() {
        withContext(Dispatchers.IO) {
            val asteroides = Network.asteroideService.getAsteroides(TODAY_DATE, Constants.API_KEY).await()
            database.nasaDao.insertAll(*asteroides.asDatabaseModel())
        }
    }

}
