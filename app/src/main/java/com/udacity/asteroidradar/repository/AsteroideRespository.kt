package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.TODAY_DATE
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.database.NasaDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import retrofit2.awaitResponse


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

//    suspend fun getHeader() {
//        withContext(Dispatchers.IO) {
//            val header = Network.asteroideService.getHeader(Constants.API_KEY).awaitResponse()
//        }
//    }

}
