package com.example.cryptotracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point for Hilt's dependency-injection graph.
 *
 * `@HiltAndroidApp` triggers code generation for the app-wide DI container,
 * which becomes the parent of every other Hilt component (Activity, ViewModel, …).
 * Registered in AndroidManifest.xml via `android:name`.
 */
@HiltAndroidApp
class CryptoTrackerApp : Application()
