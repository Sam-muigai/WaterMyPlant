package com.sam.watermyplant.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao{

    @Query("SELECT * FROM plant")
    fun getAllPlants():Flow<List<Plant>>

    @Insert
    suspend fun addPlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)

    @Query("SELECT * FROM plant WHERE id=:id")
    suspend fun getPlant(id:Int):Plant?
}