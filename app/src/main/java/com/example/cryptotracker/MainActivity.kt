package com.example.cryptotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptotracker.core.locale.LocalizedApp
import com.example.cryptotracker.core.settings.ThemeMode
import com.example.cryptotracker.navigation.CryptoNavHost
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Expose THIS activity as a ViewModelStoreOwner so screens deep inside
            // the NavHost can fetch the activity-scoped MainViewModel. We provide it
            // explicitly because (a) inside a NavHost LocalViewModelStoreOwner is the
            // nav back-stack entry, and (b) LocalActivity becomes unreliable once
            // LocalizedApp overrides LocalContext for localization.
            CompositionLocalProvider(LocalActivityViewModelStoreOwner provides this@MainActivity) {
                CryptoTrackerRoot()
            }
        }
    }
}

/**
 * Carries the Activity's [ViewModelStoreOwner] past the NavHost (which replaces
 * [androidx.lifecycle.compose.LocalLifecycleOwner]/`LocalViewModelStoreOwner` with
 * the nav entry). Any screen can read this to obtain the SHARED, activity-scoped
 * [MainViewModel] via `hiltViewModel(owner)`.
 */
val LocalActivityViewModelStoreOwner = staticCompositionLocalOf<ViewModelStoreOwner> {
    error("LocalActivityViewModelStoreOwner not provided")
}

/**
 * App root. This is where the three concepts you asked about meet:
 *
 *  1. STATE — the activity-scoped [MainViewModel] holds theme + language as
 *     StateFlows. `collectAsStateWithLifecycle` turns each flow into Compose
 *     state, so reading it here makes THIS composable recompose on change.
 *
 *  2. THEME — we translate [ThemeMode] into a dark/light boolean (SYSTEM defers
 *     to `isSystemInDarkTheme()`) and hand it to [CryptoTrackerTheme]. Change the
 *     mode → recompose → theme rebuilds → whole UI re-colors.
 *
 *  3. LANGUAGE — [LocalizedApp] overrides the locale for everything inside it, so
 *     `stringResource` re-resolves when the language changes.
 *
 * Both wrappers sit ABOVE [CryptoNavHost], so every screen on the back stack is
 * themed + localized. The state is passed down; change events come back up via
 * the ViewModel's setters.
 */
@Composable
private fun CryptoTrackerRoot(viewModel: MainViewModel = hiltViewModel()) {
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()

    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    LocalizedApp(languageTag = language.tag) {
        CryptoTrackerTheme(darkTheme = darkTheme) {
            // No settings passed down — screens read the same activity-scoped
            // MainViewModel themselves (see MarketsScreen). The root reads it only
            // to APPLY theme + locale above the nav graph.
            CryptoNavHost()
        }
    }
}
