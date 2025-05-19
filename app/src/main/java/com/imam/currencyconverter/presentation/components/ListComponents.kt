package com.imam.currencyconverter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.imam.currencyconverter.R
import com.imam.currencyconverter.presentation.ui.theme.LocalTheme

@Composable
fun ListComponents(code: String = "usa", name: String = "ua", onClick: () -> Unit) {
    val theme = LocalTheme.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(theme.background)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(theme.background)
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    onClick()
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://flagcdn.com/w320/${code}.png",
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = name, fontSize = 16.sp, color = theme.onBackground, fontFamily = FontFamily(
                    Font(R.font.poppins)
                ), fontWeight = FontWeight.Medium
            )
        }

    }
}


