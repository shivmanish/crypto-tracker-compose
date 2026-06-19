package com.example.cryptotracker.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptotracker.R
import com.example.cryptotracker.core.settings.AppLanguage
import com.example.cryptotracker.core.settings.ThemeMode
import com.example.cryptotracker.ui.components.OptionChip
import com.example.cryptotracker.ui.theme.AppTheme

/**
 * Settings sheet — a 1:1 port of the Flutter `settings_menu.dart` layout: a drag
 * handle, a "Settings" title, then a Theme and a Language section, each an
 * icon-labelled header followed by a wrap of [OptionChip]s.
 *
 * Stateless: told the current selections, reports taps via callbacks. The shared
 * MainViewModel holds the actual state (see MarketsScreen).
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SettingsBottomSheet(
    currentTheme: ThemeMode,
    currentLanguage: AppLanguage,
    onThemeSelected: (ThemeMode) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppTheme.colors.surfaceElevated,
        // Top-rounded 24, matching the Flutter container's BorderRadius.vertical(top: 24).
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        // We draw our own handle (below) to match the Flutter design exactly.
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 24.dp),
        ) {
            // Drag handle: 36 x 4, divider color, centered, 16 below.
            Spacer(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
                    .size(width = 36.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(AppTheme.colors.divider),
            )

            Text(
                text = stringResource(R.string.settings),
                color = AppTheme.colors.textPrimary,
                style = AppTheme.typography.body.copy(fontSize = 18.sp, fontWeight = FontWeight.W700),
            )

            Spacer(Modifier.height(20.dp))

            // --- Theme ---
            SectionLabel(icon = Icons.Filled.Brightness6, label = stringResource(R.string.theme))
            Spacer(Modifier.height(12.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ThemeMode.entries.forEach { mode ->
                    OptionChip(
                        label = stringResource(mode.labelRes()),
                        selected = mode == currentTheme,
                        onClick = { onThemeSelected(mode) },
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- Language ---
            SectionLabel(icon = Icons.Filled.Language, label = stringResource(R.string.language))
            Spacer(Modifier.height(12.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AppLanguage.entries.forEach { language ->
                    OptionChip(
                        label = language.displayName,
                        selected = language == currentLanguage,
                        onClick = { onLanguageSelected(language) },
                    )
                }
            }
        }
    }
}

/** Icon + label row heading a section — ports the Flutter `_SectionLabel`. */
@Composable
private fun SectionLabel(icon: ImageVector, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AppTheme.colors.textSecondary,
            modifier = Modifier.size(18.dp),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            color = AppTheme.colors.textSecondary,
            style = AppTheme.typography.body.copy(
                fontSize = 13.sp,
                fontWeight = FontWeight.W600,
                letterSpacing = 0.3.sp,
            ),
        )
    }
}

/** Maps a [ThemeMode] to its localized label resource. */
private fun ThemeMode.labelRes(): Int = when (this) {
    ThemeMode.SYSTEM -> R.string.theme_system
    ThemeMode.LIGHT -> R.string.theme_light
    ThemeMode.DARK -> R.string.theme_dark
}
