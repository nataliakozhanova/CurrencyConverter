package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.navigation.AppNavigation
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterApp()
        }
    }
}

// Полный экран с навигацией
@Composable
fun CurrencyConverterApp() {
    CurrencyConverterTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = { AppToolbar(navController = navController) }
        ) { paddingValues ->
            AppNavigation(navController = navController, modifier = Modifier.padding(paddingValues))
        }
    }
}

// Toolbar приложения
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.toolbar_title)) },
        actions = {
            if (currentRoute == "main_screen") {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications Icon"
                    )
                }
            }
        }
    )
}

// Превью для MainActivity
@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    CurrencyConverterApp()
}
