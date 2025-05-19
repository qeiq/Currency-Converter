package com.imam.currencyconverter.presentation.home_screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.imam.currencyconverter.R
import com.imam.currencyconverter.presentation.AppViewModel
import com.imam.currencyconverter.presentation.components.AppHeader
import com.imam.currencyconverter.presentation.components.SelectedCurrency
import com.imam.currencyconverter.presentation.country_screen.CountryScreenVoyager
import com.imam.currencyconverter.presentation.ui.theme.LocalTheme
import com.imam.currencyconverter.utils.getCurrencySymbol
import com.imam.currencyconverter.utils.systemBarsColor

class HomeScreenVoyager(
    val viewModel: HomeViewModel,
    val appViewModel: AppViewModel
) : Screen {
    @Composable
    override fun Content() {
        HomeScreen(viewModel, appViewModel)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, appViewModel: AppViewModel) {
    val theme = LocalTheme.current
    systemBarsColor(theme.background)

    val context = LocalContext.current
    val activity = context as? Activity
    val uiState = viewModel.uiState.collectAsState()
    val appState = appViewModel.state.collectAsState()
    val navigator = LocalNavigator.current
    LaunchedEffect(uiState.value.fromCurrencyCode) {
        appViewModel.getRates(uiState.value.fromCurrencyCode)
    }



    val amount = uiState.value.currentAmount.toDoubleOrNull() ?: 0.0
    val fromCurrency = uiState.value.fromCurrencyCode.lowercase()
    val toCurrency = uiState.value.toCurrencyCode.lowercase()

    val rateMap = appState.value.data?.rates.orEmpty()

    val convertedAmount = remember(amount, rateMap, fromCurrency, toCurrency) {
        val toRate = rateMap[toCurrency] ?: 1.0
        amount * toRate
    }

    val animatedAmount by animateFloatAsState(
        targetValue = convertedAmount.toFloat(),
        animationSpec = tween(durationMillis = 500)
    )

    val amountNotEmpty = uiState.value.currentAmount.isNotEmpty()

    val symbol = getCurrencySymbol(uiState.value.toCurrencyCode)
    var isFocused by remember { mutableStateOf(false) }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            AppHeader(
                icon = R.drawable.baseline_clear_24,
                onNavigationIconClick = {
                    activity?.finish()
                },
                title = "Currency Converter",
                showSearch = false,
                useLargeTopAppBar = false,
                listState = null,
                scrollBehavior = null,
                searchQuery = "",
                onSearchQueryChange = {}    
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(theme.background)
                .padding(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // From
                SelectedCurrency(
                    modifier = Modifier.weight(1f),
                    countryCode = uiState.value.fromCountryCode,
                    countryCurrencyName = uiState.value.fromCurrencyCode.uppercase(),
                    onClickListener = {
                        if (!appState.value.isLoading) {
                            navigator?.push(CountryScreenVoyager(viewModel, isFrom = true))
                        } else {
                            Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onCurrencyChangeIconClick = {
                        if (!appState.value.isLoading) {
                            navigator?.push(CountryScreenVoyager(viewModel, isFrom = false))
                        } else {
                            Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                IconButton(onClick = {
                    viewModel.swapCurrencies()

                }, modifier = Modifier.rotate(-17f)) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_swap_horiz_24),
                        contentDescription = null,
                        tint = theme.onBackground,
                        modifier = Modifier.size(23.dp)
                    )
                }

                // To
                SelectedCurrency(
                    modifier = Modifier.weight(1f),
                    countryCode = uiState.value.toCountryCode,
                    countryCurrencyName = uiState.value.toCurrencyCode.uppercase(),
                    onClickListener = {
                        if (!appState.value.isLoading) {
                            navigator?.push(CountryScreenVoyager(viewModel, isFrom = false))
                        } else {
                            Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onCurrencyChangeIconClick = {
                        if (!appState.value.isLoading) {
                            navigator?.push(CountryScreenVoyager(viewModel, isFrom = false))
                        } else {
                            Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }

            Spacer(Modifier.height(10.dp))
            TextField(
                value = uiState.value.currentAmount,
                onValueChange = {
                    viewModel.onEvent(HomeEvents.CurrencyAmount(it))
                },
                placeholder = {
                    Text(
                        text = "Input Value",
                        color = theme.onBackground,
                        fontFamily = FontFamily(Font(R.font.poppins)),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        isFocused = it.isFocused
                    }
                    .border(
                        width = 1.dp,
                        color = if (isFocused) theme.onBackground else theme.onSurface.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.large
                    ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = theme.background,
                    focusedContainerColor = theme.background,
                    unfocusedIndicatorColor = theme.background,
                    focusedIndicatorColor = theme.background,
                    cursorColor = theme.onBackground,
                    focusedTextColor = theme.onBackground,
                ),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                val scrollState = rememberScrollState()

                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { }
                ) {
                    Text(
                        text = "${String.format("%.2f", animatedAmount)} $symbol",
                        fontSize = 36.sp,
                        color = theme.onBackground,
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        fontWeight = FontWeight.Medium,
                    )
                }
            }



            Button(
                onClick = { viewModel.onEvent(HomeEvents.CurrencyAmount("")) },
                enabled = amountNotEmpty,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (amountNotEmpty) theme.onBackground else theme.surface.copy(
                        alpha = 0.3f
                    ),
                    contentColor = if (amountNotEmpty) theme.background else theme.onBackground.copy(
                        alpha = 0.5f
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    "Reset",
                    fontSize = 17.sp
                )
            }


        }

    }
}

