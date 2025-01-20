package com.example.currencyconverter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.currencyconverter.ui.screens.ConvertScreenContent
import com.example.currencyconverter.ui.screens.MainScreenContent

sealed class Screen(val route: String) {
    data object MainScreen : Screen("main_screen")
    data object ConvertScreen : Screen("convert_screen")
}

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route,
        modifier = modifier
    ) {
        composable(Screen.MainScreen.route) {
            MainScreenContent(onConvert = { navController.navigate(Screen.ConvertScreen.route) })
        }
        composable(Screen.ConvertScreen.route) {
            ConvertScreenContent(onBack = { navController.popBackStack() })
        }
    }
}


