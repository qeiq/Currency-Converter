package com.imam.currencyconverter.presentation.country_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.imam.currencyconverter.presentation.components.AppHeader
import com.imam.currencyconverter.presentation.components.ListComponents
import com.imam.currencyconverter.presentation.home_screen.HomeEvents
import com.imam.currencyconverter.presentation.home_screen.HomeViewModel
import com.imam.currencyconverter.presentation.ui.theme.LocalTheme
import com.imam.currencyconverter.utils.getCountryList
import com.imam.currencyconverter.utils.systemBarsColor

class CountryScreenVoyager(val viewModel: HomeViewModel, val isFrom: Boolean) : Screen {
    @Composable
    override fun Content() {
        CountryScreen(viewModel,isFrom)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryScreen(viewModel: HomeViewModel, isFrom: Boolean) {
    val theme = LocalTheme.current
    systemBarsColor(backgroundColor = theme.background)
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val countryList = getCountryList(context)
    val navigator = LocalNavigator.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()

    val searchQuery = uiState.searchQuery.trim().lowercase()

    val filteredList = remember(searchQuery) {
        if (searchQuery.isEmpty()) countryList
        else countryList.filter {
            it.name.lowercase().contains(searchQuery) ||
                    it.code.lowercase().contains(searchQuery) ||
                    it.currencyCode.lowercase().contains(searchQuery)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppHeader(
                title = "Search Currencies",
                onNavigationIconClick = {
                    navigator?.pop()
                },
                showSearch = true,
                useLargeTopAppBar = true,
                listState = listState,
                scrollBehavior = scrollBehavior,
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = { newQuery ->
                    viewModel.onEvent(HomeEvents.SearchQueryChanged(newQuery))
                }
            )
        }
    ) { innerPadding ->
        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding).background(theme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "(~_^)", color = theme.onBackground, fontSize = 94.sp)
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(theme.background)
            ) {
                items(filteredList) { country ->
                    ListComponents(
                        code = country.code,
                        name = country.name,
                        onClick = {
                            viewModel.onEvent(
                                if (isFrom) {
                                    HomeEvents.FromCountrySelected(country.code, country.currencyCode)
                                } else {
                                    HomeEvents.ToCountrySelected(country.code, country.currencyCode)
                                }
                            )
                            navigator?.pop()
                        }
                    )
                }
            }
        }

    }
}



