package com.imam.currencyconverter.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.imam.currencyconverter.presentation.ui.theme.LocalTheme
import com.imam.currencyconverter.presentation.ui.theme.appThemeColors
import org.json.JSONObject





//for the getCountryList()
data class CountryCurrency(
    val code: String,
    val name: String,
    val currencyCode: String
)



//get country list

fun getCountryList(context: Context): List<CountryCurrency> {
    val json = context.assets.open("country.json").bufferedReader().use { it.readText() }
    val jsonObject = JSONObject(json)

    val keys = jsonObject.keys()
    val list = mutableListOf<CountryCurrency>()
    while (keys.hasNext()) {
        val code = keys.next()
        val obj = jsonObject.getJSONObject(code)
        val name = obj.getString("country")
        val currencyCode = obj.getString("currency_code")
        list.add(CountryCurrency(code, name, currencyCode))
    }
    return list
}


//changing the systemBars color
@Composable
fun systemBarsColor(backgroundColor: Color) {
    val context = LocalContext.current
    // Set system bars color
    SideEffect {
        (context as? Activity)?.window?.statusBarColor = backgroundColor.toArgb()
        (context as? Activity)?.window?.navigationBarColor = backgroundColor.toArgb()
    }
}

@Composable
fun CurrencyTheme(content: @Composable () -> Unit) {
    val themeColors = if (isSystemInDarkTheme()) appThemeColors else appThemeColors

    CompositionLocalProvider(LocalTheme provides themeColors) {
        MaterialTheme{
            content()
        }
    }
}

//currency symbol
fun getCurrencySymbol(currencyCode: String): String {
    return when (currencyCode.lowercase()) {
        "usd" -> "$"
        "bdt" -> "৳"
        "eur" -> "€"
        "gbp" -> "£"
        "jpy" -> "¥"
        "aud" -> "A$"
        "cad" -> "C$"
        "chf" -> "CHF"
        "cny" -> "¥"
        "sek" -> "kr"
        "nok" -> "kr"
        "dkk" -> "kr"
        "inr" -> "₹"
        "rub" -> "₽"
        "try" -> "₺"
        "krw" -> "₩"
        "ngn" -> "₦"
        "php" -> "₱"
        "thb" -> "฿"
        "vnd" -> "₫"
        "uah" -> "₴"
        "pln" -> "zł"
        "idr" -> "Rp"
        "gel" -> "₾"
        "kzt" -> "₸"
        "azn" -> "₼"
        "pyg" -> "₲"
        "ghs" -> "₵"
        "crc" -> "₡"
        "frf" -> "₣"
        "itL" -> "₤"
        "esp" -> "₧"
        "mnt" -> "₮"
        "srl" -> "₨"
        else -> currencyCode.uppercase()
    }
}
