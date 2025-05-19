package com.imam.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.imam.currencyconverter.presentation.AppViewModel
import com.imam.currencyconverter.presentation.home_screen.HomeScreenVoyager
import com.imam.currencyconverter.presentation.home_screen.HomeViewModel
import com.imam.currencyconverter.utils.CurrencyTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyTheme {

                val viewModel: HomeViewModel = koinViewModel()
                val appViewModel: AppViewModel = koinViewModel()
                Navigator(HomeScreenVoyager(viewModel,appViewModel)){
                    SlideTransition(it)
                }
            }
        }
    }
}

