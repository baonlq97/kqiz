package com.brandon.kqiz.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brandon.kqiz.ui.screens.home.HomeScreen

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        /** Home Screen */
        composable(
            route = Screens.HomeScreen.route,
//            exitTransition = { exitTransition() },
//            popEnterTransition = { popEnterTransition() },
        ) {
            HomeScreen(navController = navController)
        }
    }
}