package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NasaDao {
    @Query("select * from databasenasa WHERE closeApproachDate >= DATE() ORDER BY closeApproachDate ASC")
    fun getAsteroides(): LiveData<List<DatabaseNasa>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: DatabaseNasa)

    @Query("DELETE FROM databasenasa WHERE closeApproachDate < DATE()")
    fun deleteAsteroids()
}

@Database(entities = [DatabaseNasa::class], version = 1)
abstract class NasaDatabase : RoomDatabase() {
    abstract val nasaDao: NasaDao
}

private lateinit var INSTANCE: NasaDatabase

fun getDatabase(context: Context): NasaDatabase {
    synchronized(NasaDatabase::class.java) {
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    NasaDatabase::class.java,
                    "asteroid").build()
        }
    }

    return INSTANCE
}
