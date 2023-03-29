package com.sam.watermyplant.presentation.homeScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sam.watermyplant.FAB
import com.sam.watermyplant.presentation.navigation.HomeNavigation
import com.sam.watermyplant.presentation.navigation.bottomNavigationItems

@Composable
fun Home(onAddClicked:() ->Unit,onPlantClicked:(Int)->Unit){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                cutoutShape = CircleShape,
                backgroundColor = MaterialTheme.colors.background
            ) {
                BottomBar(
                    navController = navController
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FAB(
                modifier = Modifier.padding(0.dp),
            ){
                onAddClicked()
            }
        },
        isFloatingActionButtonDocked = true
    ) { paddingValues ->
        HomeNavigation(
            modifier = Modifier.padding(paddingValues),
            navController = navController
        ){
            onPlantClicked(it)
        }
    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavHostController) {
    val itemPadding = 24.dp
    BottomNavigation(
        modifier = modifier,
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNavigationItems.forEachIndexed { index, screen ->
            BottomNavigationItem(
                modifier = if (index == 0) {
                    Modifier.padding(end = itemPadding)
                } else {
                    Modifier.padding(start = itemPadding)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.Icon),
                        contentDescription = screen.label
                    )
                },
                label = { Text(text = screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = MaterialTheme.colors.onBackground
            )
        }
    }
}