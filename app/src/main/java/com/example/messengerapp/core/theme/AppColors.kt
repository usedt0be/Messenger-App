package com.example.messengerapp.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val appColorScheme = AppColors(
    backgroundPrimary = Dark1,
    backgroundSecondary = Dark2,
    primary = Dark3,
    secondary = Dark4,
    tertiary = Dark5,
    accentPrimary = Blue1,
    accentSecondary = Pink1,
    textPrimary = Light1,
    textSecondary = Dark6,
    textTertiary = Dark5,
    onError = Red1,
    onDone = Green2,
    onSuccess = Blue1,
    backgroundBottomMenu = Dark2.copy(alpha = 0.5f),
    primaryContainer = Blue1,
    secondaryContainer = Light2,
    textButton = Light1
)



@Immutable
data class AppColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val backgroundPrimary: Color,
    val backgroundSecondary: Color,
    val accentPrimary: Color,
    val accentSecondary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val onError: Color,
    val onDone: Color,
    val onSuccess: Color,
    val backgroundBottomMenu: Color,
    val primaryContainer: Color,
    val secondaryContainer: Color,
    val textButton: Color
)


val LocalAppColors = staticCompositionLocalOf<AppColors> {
    appColorScheme
}


