package com.example.cryptotracker.feature.markets.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptotracker.LocalActivityViewModelStoreOwner
import com.example.cryptotracker.MainViewModel
import com.example.cryptotracker.R
import com.example.cryptotracker.feature.settings.SettingsBottomSheet
import com.example.cryptotracker.ui.components.CircleActionButton
import com.example.cryptotracker.ui.theme.AppTheme

/**
 * Home / Markets screen. The coin list/cards arrive in Phase 8; for now the real,
 * faithful piece is the [MarketsHeader] (ported from the Flutter `_Header`) plus
 * the settings flow.
 *
 * The ONLY local state is `showSettings` — a pure UI concern (is the sheet open?),
 * so it lives right here in `rememberSaveable`.
 */
@Composable
fun MarketsScreen() {
    // App-scoped settings live in MainViewModel. A plain hiltViewModel() inside a
    // NavHost would be scoped to THIS destination's back-stack entry — a different
    // instance than the app root holds. We pass the Activity owner (provided at the
    // top in MainActivity) so this resolves to the SAME instance the root uses to
    // apply theme + locale: one source of truth, no drilling.
    val activityOwner = LocalActivityViewModelStoreOwner.current
    val viewModel: MainViewModel = hiltViewModel(activityOwner)

    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()

    var showSettings by rememberSaveable { mutableStateOf(false) }

    Scaffold(containerColor = AppTheme.colors.bgBase) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            MarketsHeader(onSettingsClick = { showSettings = true })

            // Placeholder for the market content (global card, trending, list) — Phase 8.
            Box(modifier = Modifier.fillMaxSize())
        }
    }

    if (showSettings) {
        SettingsBottomSheet(
            currentTheme = themeMode,
            currentLanguage = language,
            onThemeSelected = viewModel::setThemeMode,
            onLanguageSelected = viewModel::setLanguage,
            onDismiss = { showSettings = false },
        )
    }
}

/**
 * Top header — a 1:1 port of the Flutter `_Header`:
 *  - a live indicator: a small `priceUp` dot + the "LIVE / COINGECKO" label,
 *  - the big "Markets" title (`screenTitle`),
 *  - the `more_horiz` settings button on the right, aligned to the top.
 *
 * Padding fromLTRB(16, 12, 16, 0); the action sits at the top (Row aligned Top,
 * matching Flutter's `crossAxisAlignment: start`).
 */
@Composable
private fun MarketsHeader(onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 10.dp, end = 16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(AppTheme.colors.priceUp),
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.live_label),
                    style = AppTheme.typography.label,
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.markets_title),
                style = AppTheme.typography.screenTitle,
            )
        }

        CircleActionButton(
            onClick = onSettingsClick,
            imageVector = Icons.Filled.MoreHoriz,
            contentDescription = stringResource(R.string.settings),
        )
    }
}
