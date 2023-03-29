package com.sam.watermyplant.presentation.homeScreen.plantsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.watermyplant.data.PlantRepository
import com.sam.watermyplant.data.model.Plant
import com.sam.watermyplant.util.convertToLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PlantScreenViewModel @Inject constructor(
    private val repository: PlantRepository
):ViewModel() {
    val plants = repository.getAllPlants()
    fun deletePlant(plant: Plant){
        viewModelScope.launch {
            repository.deletePlant(plant)
        }
    }
    fun getDayDifferences(stringDate:String):String{
        return if (!stringDate.isNullOrEmpty()){
            val todayDate = LocalDate.now()
            val wateringDate = stringDate.convertToLocalDate()
            try {
                val differences = ChronoUnit.DAYS.between(todayDate,wateringDate)
                "$differences"
            }catch (e: DateTimeParseException){
                e.message.toString()
            }
        }else{
            "Date is Null"
        }
    }
}