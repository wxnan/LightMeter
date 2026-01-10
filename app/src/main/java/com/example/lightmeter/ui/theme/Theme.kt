package com.example.lightmeter.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryLight.copy(alpha = 0.12f),
    onPrimaryContainer = PrimaryLight,
    inversePrimary = PrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnPrimaryLight,
    secondaryContainer = SecondaryLight.copy(alpha = 0.12f),
    onSecondaryContainer = SecondaryLight,
    tertiary = AccentLight,
    onTertiary = OnPrimaryLight,
    tertiaryContainer = AccentLight.copy(alpha = 0.12f),
    onTertiaryContainer = AccentLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceLight.copy(alpha = 0.87f),
    onSurfaceVariant = OnSurfaceLight.copy(alpha = 0.6f),
    inverseSurface = OnSurfaceLight,
    inverseOnSurface = SurfaceLight,
    error = ErrorColor,
    onError = OnPrimaryLight,
    errorContainer = ErrorColor.copy(alpha = 0.12f),
    onErrorContainer = ErrorColor,
    outline = OnSurfaceLight.copy(alpha = 0.12f),
    outlineVariant = OnSurfaceLight.copy(alpha = 0.05f),
    scrim = OnSurfaceLight,
    surfaceTint = PrimaryLight
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryDark.copy(alpha = 0.2f),
    onPrimaryContainer = PrimaryDark,
    inversePrimary = PrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnPrimaryDark,
    secondaryContainer = SecondaryDark.copy(alpha = 0.2f),
    onSecondaryContainer = SecondaryDark,
    tertiary = AccentDark,
    onTertiary = OnPrimaryDark,
    tertiaryContainer = AccentDark.copy(alpha = 0.2f),
    onTertiaryContainer = AccentDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceDark.copy(alpha = 0.87f),
    onSurfaceVariant = OnSurfaceDark.copy(alpha = 0.6f),
    inverseSurface = OnSurfaceDark,
    inverseOnSurface = SurfaceDark,
    error = ErrorColor,
    onError = OnPrimaryDark,
    errorContainer = ErrorColor.copy(alpha = 0.2f),
    onErrorContainer = ErrorColor,
    outline = OnSurfaceDark.copy(alpha = 0.12f),
    outlineVariant = OnSurfaceDark.copy(alpha = 0.05f),
    scrim = OnSurfaceDark,
    surfaceTint = PrimaryDark
)

@Composable
fun LightMeterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // window.statusBarColor = colorScheme.primary.toArgb()
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
