package com.brandon.kqiz.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.brandon.kqiz.ui.navigation.NavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    startDestination: String
) {
    val navController = rememberNavController()
    Scaffold {
        NavGraph(
            startDestination = startDestination,
            navController = navController,
        )
    }
}