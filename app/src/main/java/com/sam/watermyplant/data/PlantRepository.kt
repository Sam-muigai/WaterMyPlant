package com.sam.watermyplant.data

import com.sam.watermyplant.data.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository{

    fun getAllPlants():Flow<List<Plant>>

    suspend fun addPlant(plant: Plant)

    suspend fun deletePlant(plant: Plant)

    suspend fun updatePlant(plant: Plant)

    suspend fun getPlant(id:Int):Plant?

}