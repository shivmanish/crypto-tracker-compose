package com.example.cryptotracker.core.settings

/**
 * The three theme choices, matching the Flutter `ThemeMode` (System/Light/Dark).
 * SYSTEM means "follow the OS setting".
 */
enum class ThemeMode { SYSTEM, LIGHT, DARK }

/**
 * Supported in-app languages. [tag] is the BCP-47 code that also names the
 * `res/values-<tag>` folder (e.g. "hi" → res/values-hi). [displayName] is shown
 * in its own script so users recognize it regardless of the current language.
 */
enum class AppLanguage(val tag: String, val displayName: String) {
    ENGLISH("en", "English"),
    HINDI("hi", "हिंदी");

    companion object {
        fun fromTag(tag: String): AppLanguage = entries.firstOrNull { it.tag == tag } ?: ENGLISH
    }
}
