package com.sam.watermyplant.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sam.watermyplant.data.model.Plant
import com.sam.watermyplant.data.model.PlantDao

@Database(
    entities = [Plant::class],
    version = 2
)
abstract class PlantDatabase:RoomDatabase(){
    abstract fun dao():PlantDao
}