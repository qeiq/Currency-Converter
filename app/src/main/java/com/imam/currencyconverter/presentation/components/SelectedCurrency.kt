package com.imam.currencyconverter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.imam.currencyconverter.R
import com.imam.currencyconverter.presentation.ui.theme.LocalTheme

@Composable
fun SelectedCurrency(
    modifier: Modifier = Modifier,
    countryCurrencyName: String = "USD",
    languageIcon: Int = R.drawable.uk,
    countryCode: String = "ua",
    onClickListener: () -> Unit = {},
    onCurrencyChangeIconClick: () -> Unit = {}
) {
    val theme = LocalTheme.current
    Row(
        modifier = modifier
            .height(65.dp)
            .clip(MaterialTheme.shapes.large)
            .clickable {
                onClickListener()
            }
            .border(
                width = 1.dp,
                color = theme.onSurface.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.large
            )
            .background(theme.background)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)

        ) {
            Row(
                modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = "https://flagcdn.com/w320/${countryCode}.png",
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = countryCurrencyName.uppercase(),
                    color = theme.onBackground,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 18.sp
                )

                IconButton(onClick = { onCurrencyChangeIconClick() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = theme.onBackground
                    )
                }
            }
        }
    }
}

