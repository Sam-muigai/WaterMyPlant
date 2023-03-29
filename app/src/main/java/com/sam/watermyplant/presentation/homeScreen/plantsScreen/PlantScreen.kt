package com.sam.watermyplant.presentation.homeScreen.plantsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sam.watermyplant.presentation.homeScreen.plantsScreen.components.PlantCard
import com.sam.watermyplant.presentation.homeScreen.plantsScreen.components.PlantItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantScreen(
    modifier: Modifier = Modifier,
    viewModel: PlantScreenViewModel = hiltViewModel(),
    onPlantClick: (Int) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val plants = viewModel.plants.collectAsState(initial = emptyList()).value
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn(contentPadding = PaddingValues(bottom = 50.dp)) {
                items(plants) { plant ->
                    Spacer(modifier = Modifier.height(10.dp))
                    PlantItem(
                        plantName = plant.plantName,
                        imageUri = plant.imageUri,
                        onDelete = {
                            viewModel.deletePlant(plant)
                        },
                        onClick = {
                            onPlantClick(plant.id!!)
                        },
                        days = viewModel.getDayDifferences(plant.date)
                    )
                }
            }
        }
    }
}