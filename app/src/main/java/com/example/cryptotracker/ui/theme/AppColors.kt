package com.example.cryptotracker.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Semantic brand palette — the Compose equivalent of the Flutter
 * `ThemeExtension<AppPalette>`.
 *
 * Material3's [androidx.compose.material3.ColorScheme] only has a fixed set of
 * slots (primary, surface, …). This app needs extra app-specific colors
 * (priceUp/priceDown, favorite, shimmer, …), so we model them in our own
 * `@Immutable data class` and hand it down the tree via a CompositionLocal —
 * exactly how Flutter's ThemeExtension is read from `Theme.of(context)`.
 *
 * Read it anywhere with `AppTheme.colors` (see Theme.kt).
 */
@Immutable
data class AppColors(
    val bgBase: Color,
    val surfaceCard: Color,
    val surfaceElevated: Color,
    val inputBg: Color,
    val divider: Color,
    val accent: Color,
    val onAccent: Color,
    val priceUp: Color,
    val priceDown: Color,
    val favorite: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val shimmerBase: Color,
    val shimmerHighlight: Color,
) {
    /** Green when the value rose, red when it fell — ports `AppPalette.changeColor`. */
    fun changeColor(delta: Double): Color = if (delta >= 0) priceUp else priceDown
}

val DarkAppColors = AppColors(
    bgBase = DarkBgBase,
    surfaceCard = DarkSurfaceCard,
    surfaceElevated = DarkSurfaceElevated,
    inputBg = DarkInputBg,
    divider = DarkDivider,
    accent = DarkAccent,
    onAccent = DarkOnAccent,
    priceUp = DarkPriceUp,
    priceDown = DarkPriceDown,
    favorite = DarkFavorite,
    textPrimary = DarkTextPrimary,
    textSecondary = DarkTextSecondary,
    textMuted = DarkTextMuted,
    shimmerBase = DarkShimmerBase,
    shimmerHighlight = DarkShimmerHighlight,
)

val LightAppColors = AppColors(
    bgBase = LightBgBase,
    surfaceCard = LightSurfaceCard,
    surfaceElevated = LightSurfaceElevated,
    inputBg = LightInputBg,
    divider = LightDivider,
    accent = LightAccent,
    onAccent = LightOnAccent,
    priceUp = LightPriceUp,
    priceDown = LightPriceDown,
    favorite = LightFavorite,
    textPrimary = LightTextPrimary,
    textSecondary = LightTextSecondary,
    textMuted = LightTextMuted,
    shimmerBase = LightShimmerBase,
    shimmerHighlight = LightShimmerHighlight,
)

/**
 * CompositionLocal carrying the active palette. `static` because the value
 * object swaps wholesale on theme change (no need to track per-field reads).
 * Defaults to dark, matching the Flutter app's default appearance.
 */
val LocalAppColors = staticCompositionLocalOf { DarkAppColors }
