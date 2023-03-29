package com.sam.watermyplant.presentation.cameraScreen.addDetail

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.sam.watermyplant.R
import com.sam.watermyplant.data.model.Plant
import com.sam.watermyplant.ui.theme.WaterMyPlantTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddDetailsScreen(
    modifier: Modifier = Modifier,
    imageUri: String = "",
    viewModel: AddDetailsViewModel = hiltViewModel(),
    onAddClicked: () -> Unit
) {
    DetailScreen(
        modifier = modifier,
        imageUri = imageUri,
        viewModel = viewModel,
        onAddClicked = onAddClicked
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    imageUri: String,
    viewModel: AddDetailsViewModel,
    onAddClicked: () -> Unit
) {
    Column(modifier = modifier) {
        Image(imageUri = imageUri)
        PlantNameAndDescription(
            plantName = viewModel.plantName,
            onPlantNameChange = viewModel::onPlantNameChange,
            onAddClicked = { date, time ->
                val plant = Plant(
                    plantName = viewModel.plantName,
                    date = date,
                    time = time,
                    imageUri = imageUri
                )
                viewModel.addPlant(plant)
                onAddClicked()
            }
        )
    }
}

@Composable
fun Image(
    modifier: Modifier = Modifier,
    imageUri: String
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp),
            painter = rememberAsyncImagePainter(model = imageUri),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        CurvedSurface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun CurvedSurface(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        ),
        color = MaterialTheme.colors.background
    ) {

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantNameAndDescription(
    modifier: Modifier = Modifier,
    plantName: String,
    onAddClicked: (String, String) -> Unit,
    onPlantNameChange: (String) -> Unit
) {

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            CustomButton(
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = {
                    onAddClicked(formattedDate, formattedTime)
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add Plant Details",
                    style = MaterialTheme.typography.h2
                )
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
                        onValueChange = {
                            onPlantNameChange(it)
                        }
                    )
                }
                DateAndTimePicker(
                    label = "Date",
                    trailingIcon = R.drawable.ic_calendar,
                    period = formattedDate
                ) {
                    dateDialogState.show()
                }
                DateAndTimePicker(
                    label = "Time",
                    trailingIcon = R.drawable.ic_time,
                    period = formattedTime
                ) {
                    timeDialogState.show()
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
                initialDate = pickedDate,
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = MaterialTheme.colors.secondary
                ),
                allowedDateValidator = {
                    it >= LocalDate.now()
                },
                title = "Date to Water",
            ) {
                pickedDate = it
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
                initialTime = pickedTime,
                colors = TimePickerDefaults.colors(
                    inactiveBackgroundColor = MaterialTheme.colors.secondary
                ),
                title = "Date to Water",
            ) {
                pickedTime = it
            }
        }
    }
}


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    showIcon:Boolean = true,
    text: String = "Add To My Plants"
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        )
    ) {
        if (showIcon){
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.h3
        )
    }
}

@Preview
@Composable
fun AddButtonPreview() {
    WaterMyPlantTheme {
        CustomButton()
    }
}


@Composable
fun DateAndTimePicker(
    modifier: Modifier = Modifier,
    label: String = "Date",
    period: String = "",
    @DrawableRes trailingIcon: Int = R.drawable.ic_calendar,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(50.dp)
            .wrapContentSize()
            .clickable {
                onClick()
            },
        elevation = 0.dp,
        color = MaterialTheme.colors.background,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.h3
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = period,
                    style = MaterialTheme.typography.body1
                )
                Icon(
                    painter = painterResource(id = trailingIcon),
                    contentDescription = "Date"
                )
            }
        }
    }
}


