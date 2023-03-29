package com.sam.watermyplant.presentation.navigation

import androidx.annotation.DrawableRes
import com.sam.watermyplant.R

sealed class Screen(val route:String, @DrawableRes val Icon:Int, val label:String) {
    object Plants: Screen("plants", R.drawable.plant,"My Plants")
    object Profile: Screen("profile",R.drawable.profile,"My Profile")
    object NewPlant: Screen("new_plant",R.drawable.add,"Add")
}

val bottomNavigationItems = listOf(
    Screen.Plants,
    Screen.Profile
)

const val ADD_DETAIL_SCREEN = "add_detail_screen"