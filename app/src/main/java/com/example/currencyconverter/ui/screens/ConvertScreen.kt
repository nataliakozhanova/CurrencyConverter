package com.example.currencyconverter.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R

@Composable
fun ConvertScreenContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    // Состояние загрузки
    var isLoading by remember { mutableStateOf(false) }

    // Заглушки для данных с сервера
    val fromCurrency = "USD"
    val toCurrency = "EUR"
    val date = "01.01.2025"

    val conversionRates = listOf( // Заглушка данных о конверсии
        "1 USD = 0.92081 EUR",
        "1 USD = 1.12345 GBP",
        "1 USD = 74.5678 INR"
    )

    ScreenLayout(
        modifier = modifier,
        buttonText = stringResource(id = R.string.action_back),
        onButtonClick = onBack,
        content = {
            if (isLoading) {
                // Спиннер для отображения загрузки
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 150.dp), // Регулируем высоту размещения спиннера
                    contentAlignment = androidx.compose.ui.Alignment.TopCenter
                ) {
                    CircularProgressIndicator()
                }
            } else {
                // Данные с сервера
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    KeyValueRow(
                        label = stringResource(id = R.string.title_from_currency),
                        value = fromCurrency
                    )
                    KeyValueRow(
                        label = stringResource(id = R.string.title_to_currency),
                        value = toCurrency
                    )
                    KeyValueRow(
                        label = stringResource(id = R.string.title_date),
                        value = date
                    )

                    // Отступ сверху перед строками с курсами конверсии
                    Spacer(modifier = Modifier.height(8.dp))

                    // Динамические строки с курсами конверсии
                    conversionRates.forEach { rate ->
                        Text(
                            text = rate,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }

            // Эмуляция загрузки данных
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(2000) // Задержка в 2 секунды для симуляции
                isLoading = false
            }
        }
    )
}

@Composable
fun KeyValueRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewConvertScreenContent() {
    // Используем временные значения для отображения в превью
    ConvertScreenContent(
        onBack = {}, // Заглушка для кнопки "Back"
        modifier = Modifier
    )
}