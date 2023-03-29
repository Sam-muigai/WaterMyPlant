package com.sam.watermyplant.presentation.cameraScreen.updatePlant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.watermyplant.data.PlantRepository
import com.sam.watermyplant.data.model.Plant
import com.sam.watermyplant.util.convertToLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class UpdatePlantViewModel @Inject constructor(
    private val repository: PlantRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    init {
        val plantId = savedStateHandle.get<Int>("plantId")!!
        retrievePlant(plantId)
    }

    var plantName by mutableStateOf("")
    fun onPlantNameChange(input: String) {
        plantName = input
    }

    var plantImage by mutableStateOf("")
    val stringDate = mutableStateOf("")
    val stringTime = mutableStateOf("")
    private val date = mutableStateOf(LocalDate.now())
    private val time = mutableStateOf(LocalTime.NOON)

    fun onDateChange(input:String){
        stringDate.value = input
    }

    fun onTimeChange(input: String){
        stringTime.value = input
    }


    private val clickedPlant = MutableStateFlow<Plant>(Plant())


    private fun retrievePlant(id: Int) {
        viewModelScope.launch {
            val plant = repository.getPlant(id)!!
            clickedPlant.value = plant
            plantName = plant.plantName
            plantImage = plant.imageUri
            stringDate.value = plant.date
            stringTime.value = plant.time
        }
    }

    fun updatePlant() {
        viewModelScope.launch {
            val updatedPlant = clickedPlant.value.copy(
                plantName = plantName,
                date = stringDate.value,
                time = stringTime.value
            )
            repository.updatePlant(updatedPlant)
        }
    }

}