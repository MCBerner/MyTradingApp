package com.example.mytradingapp

sealed class NavRoutes(val route: String) {
    data object ListScreen : NavRoutes("listScreen")
    data object AddTradeItemScreen : NavRoutes(route = "profileScreen")
    data object LogRegScreen : NavRoutes(route = "logregScreen")
    data object TradeDetailsScreen : NavRoutes(route = "tradeDetailsScreen")
}