package com.sam.watermyplant.di

import android.content.Context
import androidx.room.Room
import com.sam.watermyplant.data.PlantDatabase
import com.sam.watermyplant.data.PlantRepository
import com.sam.watermyplant.data.PlantRepositoryImpl
import com.sam.watermyplant.data.model.PlantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePlantDatabase(@ApplicationContext context: Context): PlantDatabase =
        Room.databaseBuilder(context, PlantDatabase::class.java, "plantDb")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providePlantRepository(db: PlantDatabase): PlantRepository {
        return PlantRepositoryImpl(db.dao())
    }

}