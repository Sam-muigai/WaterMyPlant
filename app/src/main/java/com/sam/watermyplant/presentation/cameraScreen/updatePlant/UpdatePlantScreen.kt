package com.sam.watermyplant.presentation.cameraScreen.updatePlant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.sam.watermyplant.R
import com.sam.watermyplant.presentation.cameraScreen.addDetail.CustomButton
import com.sam.watermyplant.presentation.cameraScreen.addDetail.DateAndTimePicker
import com.sam.watermyplant.util.convertToLocalDate
import com.sam.watermyplant.util.convertToLocalTime
import com.sam.watermyplant.util.convertToString
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdatePlantScreen(
    modifier: Modifier = Modifier,
    onUpdateClicked: () -> Unit,
    viewModel: UpdatePlantViewModel = hiltViewModel()
) {
    UpdatePlantNameAndDescription(
        viewModel = viewModel,
        onUpdateClicked = onUpdateClicked
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdatePlantNameAndDescription(
    modifier: Modifier = Modifier,
    viewModel: UpdatePlantViewModel,
    onUpdateClicked: () -> Unit,
) {
    val plantName = viewModel.plantName
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            CustomButton(
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = {
                    viewModel.updatePlant()
                    onUpdateClicked()
                },
                text = "Update plant",
                showIcon = false
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colors.background
        ) {
            if (viewModel.stringDate.value.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Update Plant Details",
                        style = MaterialTheme.typography.h2
                    )
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .padding(8.dp),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = rememberAsyncImagePainter(model = viewModel.plantImage),
                            contentDescription = "plant Image",
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "Plant Name",
                            style = MaterialTheme.typography.h3.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .width(180.dp),
                            value = plantName,
                            textStyle = MaterialTheme.typography.body1,
                            onValueChange = viewModel::onPlantNameChange
                        )
                    }
                    DateAndTimePicker(
                        label = "Date",
                        trailingIcon = R.drawable.ic_calendar,
                        period = viewModel.stringDate.value
                    ) {
                        dateDialogState.show()
                    }
                    DateAndTimePicker(
                        label = "Time",
                        trailingIcon = R.drawable.ic_time,
                        period = viewModel.stringTime.value
                    ) {
                        timeDialogState.show()
                    }
                }
            }
        }
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(
                    text = "Ok",
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 12.sp
                    )
                )
                negativeButton(
                    text = "cancel",
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 12.sp
                    )
                )
            }
        ) {
            datepicker(
                initialDate = viewModel.stringDate.value.convertToLocalDate(),
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = MaterialTheme.colors.secondary
                ),
                allowedDateValidator = {
                    it >= LocalDate.now()
                },
                title = "Date to Water",
            ) {
                viewModel.onDateChange(it.convertToString())
            }
        }
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(
                    text = "Ok",
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 12.sp
                    )
                )
                negativeButton(
                    text = "cancel",
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 12.sp
                    )
                )
            }
        ) {
            timepicker(
                initialTime = viewModel.stringTime.value.convertToLocalTime(),
                title = "Date to Water",
            ) {
                viewModel.onTimeChange(it.convertToString())
            }
        }
    }
}