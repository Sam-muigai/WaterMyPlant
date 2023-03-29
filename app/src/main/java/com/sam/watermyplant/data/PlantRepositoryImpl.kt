package com.sam.watermyplant.data

import com.sam.watermyplant.data.model.Plant
import com.sam.watermyplant.data.model.PlantDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val dao: PlantDao
) : PlantRepository {
    override fun getAllPlants(): Flow<List<Plant>> = dao.getAllPlants()

    override suspend fun addPlant(plant: Plant) {
        dao.addPlant(plant)
    }

    override suspend fun deletePlant(plant: Plant) {
        dao.deletePlant(plant = plant)
    }

    override suspend fun updatePlant(plant: Plant) {
        dao.updatePlant(plant = plant)
    }

    override suspend fun getPlant(id: Int):Plant? {
        return dao.getPlant(id)
    }

}