package com.example.currencyconverter.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import java.util.Calendar

// Основной контент экрана
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    onConvert: () -> Unit
) {
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

    var amount by remember { mutableStateOf("1") }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    // Состояние для выбора даты
    val calendar = Calendar.getInstance()
    var selectedDate by remember {
        mutableStateOf(
            "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${
                calendar.get(
                    Calendar.YEAR
                )
            }"
        )
    }

    ScreenLayout(
        modifier = modifier.clickable { focusManager.clearFocus() },
        buttonText = stringResource(R.string.action_convert),
        onButtonClick = onConvert,
        content = {
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
                        selectedMultiOptions - option
                    } else {
                        selectedMultiOptions + option
                    }
                }
            )

            // Заголовок для ввода суммы
            Text(
                text = stringResource(R.string.title_amount),
                style = MaterialTheme.typography.titleMedium,
            )

            // Поле ввода суммы
            OutlinedTextField(
                value = amount,
                onValueChange = { newValue ->
                    if (newValue.matches(Regex("^\\d*([.,]\\d*)?\$"))) {
                        amount = newValue.replace(',', '.')
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused && amount.isEmpty()) {
                            amount = "1"
                        }
                        isFocused = focusState.isFocused
                    },
                shape = MaterialTheme.shapes.small,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        if (amount.isEmpty()) {
                            amount = "1"
                        }
                    }
                )
            )

            // Заголовок для выбора даты
            Text(
                text = stringResource(R.string.title_date),
                style = MaterialTheme.typography.titleMedium
            )

            // Выпадающее меню для выбора даты
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = {
                        // Показываем DatePickerDialog при нажатии
                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                selectedDate = "$dayOfMonth/${month + 1}/$year"
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                        datePickerDialog.show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.small
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = selectedDate,
                            maxLines = 1,
                            textAlign = TextAlign.Start
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                            contentDescription = "Select Date"
                        )
                    }
                }
            }
        }
    )
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
            modifier = Modifier.padding(bottom = 8.dp)
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
                        text = if (selectedOptions.isEmpty()) "Select" else selectedOptions.joinToString(
                            ", "
                        ),
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
                    .background(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.shapes.small
                    )
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

@Preview(showBackground = true)
@Composable
fun PreviewMainScreenContent() {
    CurrencyConverterTheme {
        MainScreenContent(onConvert = {})
    }
}

