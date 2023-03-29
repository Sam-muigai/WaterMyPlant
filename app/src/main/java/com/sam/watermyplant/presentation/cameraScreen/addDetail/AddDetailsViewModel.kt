package com.sam.watermyplant.presentation.cameraScreen.addDetail

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.watermyplant.data.PlantRepository
import com.sam.watermyplant.data.model.Plant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddDetailsViewModel @Inject constructor(
    private val repository: PlantRepository
) : ViewModel() {

    var plantName by mutableStateOf("")

    fun onPlantNameChange(input: String) {
        plantName = input
    }


    fun addPlant(plant: Plant) {
        viewModelScope.launch {
            repository.addPlant(plant)
        }
    }

}