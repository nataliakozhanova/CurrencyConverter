@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                // Создаём Scaffold с Toolbar и контентом
                Scaffold(
                    topBar = { AppToolbar() }
                ) { paddingValues ->
                    // Контент экрана
                    MainScreenContent(Modifier.padding(paddingValues))
                }
            }
        }
    }
}

// Toolbar с названием приложения
@Composable
fun AppToolbar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        actions = {
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications Icon"
                )
            }
        }
    )
}

// Основной контент экрана
@Composable
fun MainScreenContent(modifier: Modifier = Modifier) {
    val singleMenuItems = listOf(
        stringResource(R.string.currency1),
        stringResource(R.string.currency2),
        stringResource(R.string.currency3)
    )
    var selectedSingleOption by remember { mutableStateOf(listOf(singleMenuItems.first())) }

    val multiMenuItems = listOf(
        stringResource(R.string.currency1),
        stringResource(R.string.currency2),
        stringResource(R.string.currency3),
        stringResource(R.string.currency4)
    )
    var selectedMultiOptions by remember { mutableStateOf(multiMenuItems) } // По умолчанию все выбраны

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Первое меню (один выбор)
        CustomDropdownMenu(
            title = stringResource(R.string.title_from_currency),
            menuItems = singleMenuItems,
            selectedOptions = selectedSingleOption,
            singleSelection = true,
            onOptionSelected = { selectedSingleOption = listOf(it) }
        )

        // Второе меню (многократный выбор)
        CustomDropdownMenu(
            title = stringResource(R.string.title_to_currency),
            menuItems = multiMenuItems,
            selectedOptions = selectedMultiOptions,
            singleSelection = false,
            onOptionSelected = { option ->
                selectedMultiOptions = if (selectedMultiOptions.contains(option)) {
                    selectedMultiOptions - option // Удалить выбранное
                } else {
                    selectedMultiOptions + option // Добавить выбранное
                }
            }
        )
    }
}

@Composable
fun CustomDropdownMenu(
    title: String,
    menuItems: List<String>,
    selectedOptions: List<String>,
    onOptionSelected: (String) -> Unit,
    singleSelection: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // Заголовок меню
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
        )

        // Кнопка для раскрытия меню
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { expanded = !expanded }, // Меняем состояние при клике
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Отображение выбранных значений
                    Text(
                        text = if (selectedOptions.isEmpty()) "Select" else selectedOptions.joinToString(", "),
                        maxLines = 1
                    )
                    // Динамическая иконка
                    Icon(
                        painter = if (expanded) {
                            painterResource(id = R.drawable.baseline_arrow_drop_up_24)
                        } else {
                            painterResource(id = R.drawable.baseline_arrow_drop_down_24)
                        },
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
            }

            // Выпадающее меню
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }, // Скрываем меню при клике вне его
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                menuItems.forEach { option ->
                    val isSelected = selectedOptions.contains(option)
                    DropdownMenuItem(
                        onClick = {
                            if (singleSelection) {
                                onOptionSelected(option) // Выбираем одно значение
                                expanded = false // Закрываем меню после выбора
                            } else {
                                // Добавляем или удаляем опцию для множественного выбора
                                onOptionSelected(option)
                            }
                        },
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(option)
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected Icon"
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}


// Превью для быстрого тестирования
@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    CurrencyConverterTheme {
        Scaffold(
            topBar = { AppToolbar() }
        ) { paddingValues ->
            MainScreenContent(Modifier.padding(paddingValues))
        }
    }
}
