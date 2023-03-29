package com.sam.watermyplant.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sam.watermyplant.presentation.homeScreen.Home
import com.sam.watermyplant.presentation.homeScreen.plantsScreen.PlantScreen

@Composable
fun HomeNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onPlantClicked: (Int) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "bottom_navigation"
    ) {
        bottomNavigation(){
            onPlantClicked(it)
        }
    }
}


fun NavGraphBuilder.bottomNavigation(onPlantClicked:(Int)->Unit) {
    navigation(
        route = "bottom_navigation",
        startDestination = Screen.Plants.route
    ) {
        composable(Screen.Plants.route) {
            PlantScreen{
                onPlantClicked(it)
            }
        }
        composable(Screen.Profile.route) {
            Screen(text = "Profile")
        }
    }
}

@Composable
fun Screen(text: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = text, modifier = Modifier.align(Alignment.Center))
    }
}
