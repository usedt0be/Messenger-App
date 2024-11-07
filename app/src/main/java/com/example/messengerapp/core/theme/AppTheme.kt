package com.example.messengerapp.core.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun AppTheme(
    colors: AppColors= AppTheme.colors,
    typography: AppTypography = AppTheme.typography,
    content: @Composable () -> Unit
) {

//    val appColorScheme = remember { appColorScheme }
    val sysUiController = rememberSystemUiController()

    SideEffect {
        sysUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true,
        )
    }

    MaterialTheme(

        typography = Typography
    ) {
        CompositionLocalProvider(
            LocalAppColors provides colors,
            LocalAppTypography provides typography,
            LocalContentColor provides colors.textPrimary,
            content = content
        )
    }


}


object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

}



