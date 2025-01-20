package com.example.currencyconverter.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

@Composable
fun ConvertScreenContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    ScreenLayout(
        modifier = modifier,
        buttonText = stringResource(id = R.string.action_back),
        onButtonClick = onBack,
        content = { // Передаем контент через параметр content
            Text(text = stringResource(id = R.string.convert_screen_message))
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewConvertScreenContent() {
    CurrencyConverterTheme {
        ConvertScreenContent(onBack = {})
    }
}