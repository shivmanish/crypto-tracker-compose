package com.example.cryptotracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.cryptotracker.R

/**
 * OpenSans — the app's brand font, ported from the Flutter `fontFamily: 'OpenSans'`.
 * The single ttf in res/font is a variable font, so one [Font] covers all weights.
 */
val OpenSans = FontFamily(Font(R.font.open_sans))

/**
 * App-specific text styles — the Compose equivalent of the Flutter
 * `ThemeExtension<AppTypography>`. Material3's [Typography] (below) covers the
 * generic slots; these are the bespoke styles the screens actually use.
 *
 * Read anywhere with `AppTheme.typography` (see Theme.kt).
 *
 * Note: Flutter `height` (line-height multiplier) maps to Compose `lineHeight`
 * expressed in `.em`; `letterSpacing` in logical px maps to `.sp`.
 */
@Immutable
data class AppTypography(
    val screenTitle: TextStyle,
    val appBarTitle: TextStyle,
    val label: TextStyle,
    val statValue: TextStyle,
    val priceLarge: TextStyle,
    val coinName: TextStyle,
    val coinSymbol: TextStyle,
    val coinPrice: TextStyle,
    val percent: TextStyle,
    val body: TextStyle,
    val about: TextStyle,
    val inputText: TextStyle,
    val inputHint: TextStyle,
)

private fun baseAppTypography(
    primary: androidx.compose.ui.graphics.Color,
    secondary: androidx.compose.ui.graphics.Color,
    muted: androidx.compose.ui.graphics.Color,
) = AppTypography(
    screenTitle = TextStyle(
        fontFamily = OpenSans, color = primary, fontSize = 34.sp,
        fontWeight = FontWeight.W800, lineHeight = 1.1.em, letterSpacing = (-0.5).sp,
    ),
    appBarTitle = TextStyle(
        fontFamily = OpenSans, color = secondary, fontSize = 16.sp,
        fontWeight = FontWeight.W500, letterSpacing = 1.0.sp,
    ),
    label = TextStyle(
        fontFamily = OpenSans, color = muted, fontSize = 11.sp,
        fontWeight = FontWeight.W500, letterSpacing = 1.0.sp,
    ),
    statValue = TextStyle(
        fontFamily = OpenSans, color = primary, fontSize = 22.sp,
        fontWeight = FontWeight.W700, letterSpacing = (-0.3).sp,
    ),
    priceLarge = TextStyle(
        fontFamily = OpenSans, color = primary, fontSize = 40.sp,
        fontWeight = FontWeight.W700, lineHeight = 1.0.em, letterSpacing = (-1.0).sp,
    ),
    coinName = TextStyle(
        fontFamily = OpenSans, color = primary, fontSize = 15.sp, fontWeight = FontWeight.W600,
    ),
    coinSymbol = TextStyle(
        fontFamily = OpenSans, color = secondary, fontSize = 12.sp,
        fontWeight = FontWeight.W500, letterSpacing = 0.3.sp,
    ),
    coinPrice = TextStyle(
        fontFamily = OpenSans, color = primary, fontSize = 15.sp, fontWeight = FontWeight.W600,
    ),
    // percent color is set at call site (green/red); only metrics are fixed here.
    percent = TextStyle(
        fontFamily = OpenSans, fontSize = 12.sp, fontWeight = FontWeight.W600,
    ),
    body = TextStyle(
        fontFamily = OpenSans, color = primary, fontSize = 14.sp,
        fontWeight = FontWeight.W400, lineHeight = 1.4.em,
    ),
    about = TextStyle(
        fontFamily = OpenSans, color = secondary, fontSize = 14.sp,
        fontWeight = FontWeight.W400, lineHeight = 1.55.em,
    ),
    inputText = TextStyle(
        fontFamily = OpenSans, color = primary, fontSize = 15.sp, fontWeight = FontWeight.W400,
    ),
    inputHint = TextStyle(
        fontFamily = OpenSans, color = muted, fontSize = 15.sp, fontWeight = FontWeight.W400,
    ),
)

// Colors pulled from the palette tokens, mirroring AppTypography.dark / .light.
val DarkAppTypography = baseAppTypography(DarkTextPrimary, DarkTextSecondary, DarkTextMuted)
val LightAppTypography = baseAppTypography(LightTextPrimary, LightTextSecondary, LightTextMuted)

val LocalAppTypography = staticCompositionLocalOf { DarkAppTypography }

/**
 * Material3 [Typography] used by stock Material components (Button, etc.).
 * We only override the font family so anything not using [AppTypography] still
 * renders in OpenSans. Avoid unused-decoration warnings by keeping defaults.
 */
val AppMaterialTypography = Typography().run {
    copy(
        bodyLarge = bodyLarge.copy(fontFamily = OpenSans, textDecoration = TextDecoration.None),
        bodyMedium = bodyMedium.copy(fontFamily = OpenSans),
        bodySmall = bodySmall.copy(fontFamily = OpenSans),
        titleLarge = titleLarge.copy(fontFamily = OpenSans),
        titleMedium = titleMedium.copy(fontFamily = OpenSans),
        titleSmall = titleSmall.copy(fontFamily = OpenSans),
        labelLarge = labelLarge.copy(fontFamily = OpenSans),
        labelMedium = labelMedium.copy(fontFamily = OpenSans),
        labelSmall = labelSmall.copy(fontFamily = OpenSans),
        headlineLarge = headlineLarge.copy(fontFamily = OpenSans),
        headlineMedium = headlineMedium.copy(fontFamily = OpenSans),
        headlineSmall = headlineSmall.copy(fontFamily = OpenSans),
    )
}
