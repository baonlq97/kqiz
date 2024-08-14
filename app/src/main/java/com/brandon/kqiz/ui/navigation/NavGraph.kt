package com.brandon.kqiz.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.brandon.kqiz.ui.screens.home.HomeScreen
import com.brandon.kqiz.ui.screens.kqiz.KqizScreen

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
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
        ) {
            HomeScreen(navController = navController)
        }

        /** Kqiz Screen */
        composable(
            route = Screens.KqizScreen.route,
            arguments = listOf(
                navArgument(TEST_ID_ARG_KEY) {
                    type = NavType.StringType
                },
                navArgument(TEST_INDEX_ARG_KEY) {
                    type = NavType.StringType
                },
            ),
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() },
        ) { backStackEntry ->
            val questionId = backStackEntry.arguments!!.getString(TEST_ID_ARG_KEY)!!
            val questionIndex = backStackEntry.arguments!!.getString(TEST_INDEX_ARG_KEY)!!
            KqizScreen(navController = navController, questionId, questionIndex)
        }
    }
}