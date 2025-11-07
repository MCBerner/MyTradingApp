package com.example.mytradingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mytradingapp.model.AuthenticationViewModel
import com.example.mytradingapp.model.TradeItem
import com.example.mytradingapp.model.TradeItemsViewModel
import com.example.mytradingapp.screens.ListScreen
import com.example.mytradingapp.screens.LogRegScreen
import com.example.mytradingapp.screens.ProfileScreen
import com.example.mytradingapp.screens.TradeItemDetails
import com.example.mytradingapp.ui.theme.MyTradingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTradingAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: TradeItemsViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.ListScreen.route

    ) {
        composable(NavRoutes.ListScreen.route) {
            ListScreen(
                onProfileClick = { navController.navigate(NavRoutes.ProfileScreen.route) },
                onItemClick = { itemId ->
                    navController.navigate(NavRoutes.TradeDetailsScreen.route + "/$itemId")
                },
                onLogRegClick = { navController.navigate(NavRoutes.LogRegScreen.route) },
                onAddClick = {navController.navigate(NavRoutes.ProfileScreen.route)}
            )
        }
        composable(NavRoutes.LogRegScreen.route) {
            LogRegScreen(
                user = authenticationViewModel.user,
                message = authenticationViewModel.message,
                signIn = { email, password -> authenticationViewModel.signIn(email, password) },
                register = { email, password -> authenticationViewModel.register(email, password) },
                onBackClick = {
                    navController.popBackStack()
                })
        }
        composable(NavRoutes.ProfileScreen.route) {
            ProfileScreen(onBackClick = { navController.popBackStack() })
        }
        composable(
            route = NavRoutes.TradeDetailsScreen.route + "/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tradeItemId = backStackEntry.arguments?.getInt("itemId")
            val tradeItem =
                viewModel.tradeItemsLiveData.value.find { it.id == tradeItemId } ?: TradeItem(
                    description = "Not found",
                    price = 0.0, sellerEmail = "Not found",
                    sellerPhone = ""
                )
            TradeItemDetails(
                item = tradeItem,
                onBackClick = { navController.popBackStack() }
            )
        }
    }

}
/*private fun getDateTime*/
