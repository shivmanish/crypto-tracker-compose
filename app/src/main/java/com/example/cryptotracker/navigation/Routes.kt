package com.example.cryptotracker.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation destinations.
 *
 * Each route is a `@Serializable` type (not a magic string like "markets"). The
 * Navigation-Compose library serializes these into the back stack, so you navigate
 * with real Kotlin objects and get compile-time safety + autocomplete.
 *
 * A destination with no arguments is an `object`. One WITH arguments becomes a
 * `data class` — e.g. the coin-detail route we'll add in a later phase:
 *
 *     @Serializable data class CoinDetailRoute(val coinId: String)
 *
 * …and you'd navigate with `navController.navigate(CoinDetailRoute(id))`.
 */
@Serializable
object MarketsRoute
