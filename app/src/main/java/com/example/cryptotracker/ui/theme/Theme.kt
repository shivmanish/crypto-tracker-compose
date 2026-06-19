package com.example.cryptotracker.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Maps the brand [AppColors] onto the fixed Material3 [ColorScheme] slots, so
 * stock Material components (Button, Card, …) also pick up the brand look.
 * Mirrors `ColorScheme.fromSeed(... overrides ...)` in the Flutter `app_theme.dart`.
 */
private fun darkScheme(c: AppColors): ColorScheme = darkColorScheme(
    primary = c.accent,
    onPrimary = c.onAccent,
    background = c.bgBase,
    onBackground = c.textPrimary,
    surface = c.bgBase,
    onSurface = c.textPrimary,
    surfaceVariant = c.surfaceCard,
    onSurfaceVariant = c.textSecondary,
    error = c.priceDown,
    outline = c.divider,
)

private fun lightScheme(c: AppColors): ColorScheme = lightColorScheme(
    primary = c.accent,
    onPrimary = c.onAccent,
    background = c.bgBase,
    onBackground = c.textPrimary,
    surface = c.bgBase,
    onSurface = c.textPrimary,
    surfaceVariant = c.surfaceCard,
    onSurfaceVariant = c.textSecondary,
    error = c.priceDown,
    outline = c.divider,
)

/**
 * Root theme. Wrap the whole app in this (`setContent { CryptoTrackerTheme { … } }`).
 *
 * Dynamic color (Material You) is intentionally OFF — this app has a fixed brand
 * palette, exactly like the Flutter version, so wallpaper-derived colors must not
 * override it.
 *
 * @param darkTheme defaults to the system setting. Phase 10 will drive this from a
 * DataStore-backed system/light/dark preference (the `AppThemeManager` equivalent).
 */
@Composable
fun CryptoTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val appColors = if (darkTheme) DarkAppColors else LightAppColors
    val appTypography = if (darkTheme) DarkAppTypography else LightAppTypography
    val colorScheme = if (darkTheme) darkScheme(appColors) else lightScheme(appColors)

    // Match status-bar icon contrast to the theme (light icons on dark bg, vice versa).
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalAppTypography provides appTypography,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppMaterialTypography,
            content = content,
        )
    }
}

/**
 * Convenience accessor — read brand tokens via `AppTheme.colors` / `AppTheme.typography`,
 * the way you'd reach into a Flutter ThemeExtension. Example:
 * `Text(..., color = AppTheme.colors.priceUp, style = AppTheme.typography.coinName)`.
 */
object AppTheme {
    val colors: AppColors
        @Composable @ReadOnlyComposable get() = LocalAppColors.current

    val typography: AppTypography
        @Composable @ReadOnlyComposable get() = LocalAppTypography.current
}
