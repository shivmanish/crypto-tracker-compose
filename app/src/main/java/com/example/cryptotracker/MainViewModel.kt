package com.example.cryptotracker

import androidx.lifecycle.ViewModel
import com.example.cryptotracker.core.settings.AppLanguage
import com.example.cryptotracker.core.settings.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * App-wide UI state: the chosen theme mode and language.
 *
 * Scoped to the Activity (obtained once in MainActivity), so the theme wrapper
 * and every screen share the SAME instance — change it from the settings sheet
 * and the whole app reacts.
 *
 * State is exposed as read-only [StateFlow]s; the UI changes it only through the
 * `set…` events. This is "state down, events up".
 *
 * NOTE: in-memory only for now — the selection resets on app restart. Phase 4/10
 * will back these with DataStore (the `AppThemeManager`/`shared_preferences`
 * equivalent) so the choice persists. The UI wiring below won't change then.
 */
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _language = MutableStateFlow(AppLanguage.ENGLISH)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    fun setLanguage(language: AppLanguage) {
        _language.value = language
    }
}
