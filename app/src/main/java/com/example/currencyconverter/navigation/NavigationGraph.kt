//package com.example.currencyconverter.navigation
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.currencyconverter.MainScreenContent
//
//@Composable
//fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
//    NavHost(
//        navController = navController,
//        startDestination = "main_screen", // Начальный экран
//        modifier = modifier.fillMaxSize()
//    ) {
//        composable("main_screen") {
//            MainScreenContent(
//                onConvertClick = { navController.navigate("convert_screen") }
//            )
//        }
//
//        composable("convert_screen") {
//            ConvertScreen(
//                onBackClick = { navController.popBackStack() }
//            )
//        }
//    }
//}

