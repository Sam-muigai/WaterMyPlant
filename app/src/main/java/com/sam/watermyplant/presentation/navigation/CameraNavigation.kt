package com.sam.watermyplant.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sam.watermyplant.presentation.cameraScreen.Camera
import com.sam.watermyplant.presentation.cameraScreen.addDetail.AddDetailsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CameraNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onAddClicked: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "camera"
    ) {
        cameraScreen(onAddClicked = onAddClicked){
            navController.navigate("$ADD_DETAIL_SCREEN?imageUri=$it")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.cameraScreen(onAddClicked:()->Unit,onCapture:(String)->Unit) {
    navigation(
        route = "camera",
        startDestination = Screen.NewPlant.route
    ) {
        composable(Screen.NewPlant.route) {
            Camera{
                onCapture(it.toString())
            }
        }
        composable(
            "$ADD_DETAIL_SCREEN?imageUri={imageUri}", arguments =
            listOf(
                navArgument("imageUri") {
                    type = NavType.StringType
                }
            )
        ) {navBackStack ->
            val imageUri = navBackStack.arguments?.getString("imageUri")!!
            AddDetailsScreen(imageUri = imageUri, onAddClicked =onAddClicked)
        }
    }
}