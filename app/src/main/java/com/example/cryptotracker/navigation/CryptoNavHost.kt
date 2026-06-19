package com.example.cryptotracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cryptotracker.feature.markets.presentation.MarketsScreen

/**
 * The app's navigation graph. Today it holds one destination ([MarketsRoute]);
 * later phases register more (`composable<CoinDetailRoute> { … }`) and the
 * Markets screen will call `navController.navigate(CoinDetailRoute(id))`.
 *
 * HOW IT CONNECTS:
 *  - [NavHost] owns the back stack and swaps the visible `composable` based on the
 *    current route. `startDestination` is what shows first.
 *  - Note there are NO settings params here. App-scoped state (theme/language) is
 *    NOT drilled through navigation — each screen that needs it reads the
 *    activity-scoped MainViewModel directly. So the graph stays focused on routing.
 */
@Composable
fun CryptoNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = MarketsRoute,
    ) {
        composable<MarketsRoute> {
            MarketsScreen()
        }
    }
}
