package com.imam.currencyconverter.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imam.currencyconverter.R
import com.imam.currencyconverter.presentation.ui.theme.LocalTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(
    title: String,
    icon: Int = R.drawable.baseline_arrow_back_24,
    onNavigationIconClick: () -> Unit = {},
    showSearch: Boolean,
    useLargeTopAppBar: Boolean,
    listState: LazyListState? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    val theme = LocalTheme.current
    var isSearching by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    var isScrolling by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Observe scroll position and auto hide/show search
    LaunchedEffect(listState) {
        listState?.let { state ->
            snapshotFlow { state.firstVisibleItemIndex }
                .collect { index ->
                    if (index > 0 && isSearching) {
                        isSearching = false
                    } else if (index == 0 && !isSearching && showSearch) {
                        isSearching = true
                    }
                }
        }
    }

    val titleContent: @Composable () -> Unit = {
        if (isSearching) {
            TextField(
                textStyle = TextStyle(fontSize = 16.sp),
                value = searchQuery,  // Use state from ViewModel
                onValueChange = { newText ->
                    onSearchQueryChange(newText)  // Notify ViewModel of change
                },
                placeholder = {
                    Text(
                        text = "Search...",
                        color = theme.onBackground,
                        fontFamily = FontFamily(Font(R.font.poppins)),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused = it.isFocused }
                    .height(60.dp)
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
                shape = MaterialTheme.shapes.large,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        } else {
            Text(text = title, color = theme.onBackground)
        }
    }

    val actionsContent: @Composable RowScope.() -> Unit = {
        if (showSearch) {
            IconButton(onClick = {
                if (!isScrolling) {
                    coroutineScope.launch {
                        isScrolling = true
                        val currentIndex = listState?.firstVisibleItemIndex ?: 0
                        if (currentIndex > 10) {
                            listState?.scrollToItem(10)
                        }
                        listState?.animateScrollToItem(0)
                        isSearching = true
                        isScrolling = false
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = theme.onBackground
                )
            }
        }
    }

    if (useLargeTopAppBar) {
        LargeTopAppBar(
            title = titleContent,
            navigationIcon = {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(painter = painterResource(icon), null, tint = theme.onBackground)
                }
            },
            actions = actionsContent,
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = theme.background,
                scrolledContainerColor = theme.background
            )
        )
    } else {
        TopAppBar(
            title = titleContent,
            navigationIcon = {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(painter = painterResource(icon), null, tint = theme.onBackground)
                }
            },
            actions = actionsContent,
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = theme.background,
                scrolledContainerColor = theme.background
            )
        )
    }
}




