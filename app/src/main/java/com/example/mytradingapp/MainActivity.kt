package com.example.mytradingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytradingapp.model.AuthenticationViewModel
import com.example.mytradingapp.model.TradeItem
import com.example.mytradingapp.model.TradeItemsViewModel
import com.example.mytradingapp.screens.AddTradeItemScreen
import com.example.mytradingapp.screens.ListScreen
import com.example.mytradingapp.screens.LogRegScreen
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
    modifier: Modifier = Modifier,
    viewModel: TradeItemsViewModel = viewModel(),
    //navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val tradeItems = viewModel.tradeItems.value
    val errorMessage = viewModel.errorMessage.value
    val isLoadingTradeItem = viewModel.isLoadingTradeItems.value

    NavHost(navController = navController, startDestination = NavRoutes.ListScreen.route) {
        composable(NavRoutes.ListScreen.route) {
            ListScreen(
                modifier = modifier,
                tradeItems = tradeItems,
                errorMessage = errorMessage,
                tradeItemsLoading = isLoadingTradeItem,
                onTradeItemsReload = { viewModel.reload() },
                onItemClick = { tradeItemId -> navController.navigate(NavRoutes.TradeDetailsScreen.route + "/${tradeItemId}") },
                onLogRegClick = { navController.navigate(NavRoutes.LogRegScreen.route) },
                onAddClick = { navController.navigate(NavRoutes.AddTradeItemScreen.route) },
                filterByDescription = { viewModel.filterByDescription(it) },
                sortByDescription = { viewModel.sortByDescription(ascending = it) },
                sortByPrice = { viewModel.sortByPrice(ascending = it) }
            )
        }
        composable(NavRoutes.LogRegScreen.route) {
            LogRegScreen(
                user = authenticationViewModel.user,
                message = authenticationViewModel.message,
                signIn = { email, password -> authenticationViewModel.signIn(email, password) },
                register = { email, password -> authenticationViewModel.register(email, password) },
                onSignOut = { authenticationViewModel.signOut() },
                onBackClick = {
                    navController.popBackStack()
                })
        }
        composable(
            route = NavRoutes.TradeDetailsScreen.route + "/{itemId}",
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")?.toIntOrNull()
            val tradeItem = viewModel.tradeItems.value.find { it.id == itemId } ?: TradeItem(
                description = "no item found",
                price = 0,
                sellerEmail = "no mail found",
                sellerPhone = "no number found"
            )
            TradeItemDetails(
                modifier = modifier,
                tradeItem = tradeItem,
                onBackClick = { navController.popBackStack() },
            )
        }
        composable(NavRoutes.AddTradeItemScreen.route) {
            val currentUserEmail = authenticationViewModel.user?.email ?: "chami@gmail.com"

            AddTradeItemScreen(
                modifier = modifier,
                sellerEmail = currentUserEmail,
                addTradeItem = { tradeItem ->
                    viewModel.addTradeItem(tradeItem) },
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
/*private fun getDateTime
*
*      //onUpdate = { id: Int, tradeItem: TradeItem -> viewModel.update(id, tradeItem) },
                //onNavigateBack = { navController.popBackStack() })
* */
