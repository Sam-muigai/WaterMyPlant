package com.sam.watermyplant

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sam.watermyplant.presentation.cameraScreen.CameraScreen
import com.sam.watermyplant.presentation.cameraScreen.updatePlant.UpdatePlantScreen
import com.sam.watermyplant.presentation.homeScreen.Home
import com.sam.watermyplant.presentation.navigation.*
import com.sam.watermyplant.ui.theme.WaterMyPlantTheme
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterMyPlantTheme {
                Navigation()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            Home(
                onAddClicked = {
                    navController.navigate("camera")
                },
                onPlantClicked = {
                    navController.navigate("update?plantId=$it")
                }
            )
        }
        composable("camera") {
            CameraScreen {
                navController.popBackStack()
            }
        }
        composable(
            route = "update?plantId={plantId}",
            arguments = listOf(
                navArgument("plantId") {
                    type = NavType.IntType
                }
            )
        ) {
            UpdatePlantScreen(
                onUpdateClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun FAB(
    modifier: Modifier = Modifier,
    onAddPlantClicked: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onAddPlantClicked,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Icon(
            modifier = Modifier.size(45.dp),
            painter = painterResource(id = R.drawable.add),
            contentDescription = Screen.NewPlant.label
        )
    }
}