package com.Dasha.redmaterna.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = AppBackgroundDark,
    surface = AppBackgroundDark,
    onBackground = CardBackgroundLight,
    onSurface = CardBackgroundLight,
    surfaceVariant = CardBackgroundDark,
    outlineVariant = BorderDark,
    onSurfaceVariant = SecondaryTextDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = AppBackgroundLight,
    surface = AppBackgroundLight,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = CardBackgroundLight,
    outlineVariant = BorderLight,
    onSurfaceVariant = SecondaryTextLight

    /* Other default colors to override
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    */
)

@Composable
fun RedMaternaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set to false if you want your custom colors to take precedence over system dynamic colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
