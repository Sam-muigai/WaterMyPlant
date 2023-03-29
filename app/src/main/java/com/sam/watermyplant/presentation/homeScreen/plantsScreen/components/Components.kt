package com.sam.watermyplant.presentation.homeScreen.plantsScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sam.watermyplant.R
import com.sam.watermyplant.ui.theme.WaterMyPlantTheme


@Composable
fun PlantItem(
    modifier: Modifier = Modifier,
    plantName: String,
    imageUri: String,
    days: String,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
    ) {
        Text(
            text = plantName,
            style = MaterialTheme.typography.h2.copy(
                fontWeight = FontWeight.Bold
            )
        )
        PlantCard(
            imageUri = imageUri,
            onDelete = onDelete,
            onClick = onClick,
            days = days
        )
    }
}


@Composable
fun PlantCard(
    modifier: Modifier = Modifier,
    imageUri: String,
    days:String,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(MaterialTheme.shapes.small)
            .clickable { onClick() }
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(context)
                .data(imageUri)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Light
                        )
                    ) {
                        append("Next watering in\n")
                    }
                    if (days == "0"){
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                        ) {
                            append("Today")
                        }
                    }else{
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                        ) {
                            append("$days days")
                        }
                    }
                },
                style = MaterialTheme.typography.h3,
                color = Color.White
            )
            Icon(
                modifier = Modifier.size(30.dp),
                tint = Color.White,
                painter = painterResource(id = R.drawable.drops),
                contentDescription = "Water Drops"
            )
        }
        Icon(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .clickable {
                    onDelete()
                },
            imageVector = Icons.Default.Delete,
            contentDescription = null
        )
    }
}

