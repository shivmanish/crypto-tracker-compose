package com.example.cryptotracker.core.locale

import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import java.util.Locale

/**
 * Applies an in-app language by swapping the *resources* seen by everything inside
 * [content], so `stringResource` resolves against `res/values-<languageTag>`.
 *
 * HOW IT CONNECTS:
 *  - `stringResource(id)` reads localized strings from `LocalResources` /
 *    `LocalContext.current.resources`, and tracks `LocalConfiguration.current` so it
 *    recomposes when the config changes.
 *  - When [languageTag] changes (driven by app state), this recomposes, a new
 *    localized context is provided, and every `stringResource` below re-resolves —
 *    instantly, no Activity restart. Same "state at top → CompositionLocal → read
 *    below" idea as theming.
 *
 * WHY A ContextWrapper (and NOT `createConfigurationContext` directly as LocalContext):
 *  `createConfigurationContext(config)` returns a *detached* app-level `ContextImpl`.
 *  If we provided THAT as `LocalContext`, anything below that needs the Activity —
 *  notably `hiltViewModel()`, which reads `LocalContext` to build its factory —
 *  would crash ("Expected an activity context … found ContextImpl"). So instead we
 *  wrap the ORIGINAL (Activity) context and override only `getResources()`. Hilt's
 *  Activity lookup unwraps `ContextWrapper.baseContext` and still finds the Activity,
 *  while resources resolve to the chosen locale.
 */
@Composable
fun LocalizedApp(
    languageTag: String,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    // Read config via LocalConfiguration (Compose-tracked) so a system config change
    // — rotation, system dark mode, font scale — rebuilds our localized copy too.
    val configuration = LocalConfiguration.current
    val localizedContext = remember(languageTag, configuration, context) {
        val config = Configuration(configuration).apply {
            setLocale(Locale.forLanguageTag(languageTag))
        }
        val localizedResources = context.createConfigurationContext(config).resources
        object : ContextWrapper(context) {
            override fun getResources(): Resources = localizedResources
        }
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalResources provides localizedContext.resources,
        LocalConfiguration provides localizedContext.resources.configuration,
        content = content,
    )
}
