@file:Suppress("SpellCheckingInspection", "DEPRECATION")

package com.example.mytradingapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytradingapp.model.TradeItem
import com.example.mytradingapp.model.TradeItemsViewModel
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    tradeItems: List<TradeItem>,
    errorMessage: String,
    tradeItemsLoading: Boolean,
    onTradeItemsReload: () -> Unit,
    onAddClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    sortByDescription: (Boolean) -> Unit,
    sortByPrice: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onLogin: () -> Unit = {},
    onSignOut: () -> Unit = {},
    onDeleteItem: (TradeItem) -> Unit,
    currentUser: FirebaseUser?,
    viewModel: TradeItemsViewModel
) {

        Scaffold(modifier = modifier,
        topBar = {
            MyTopBar(
                isSignedIn = (currentUser != null),
                onLogin = onLogin , onSignOut = onSignOut)
        },
            floatingActionButtonPosition = FabPosition.End ,
        floatingActionButton = {
            if (currentUser != null)
            FloatingActionButton(
                onClick = { onAddClick()},
                modifier = Modifier.padding(bottom = 40.dp)) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TradeItemListPanel(
                tradeItems = tradeItems,
                errorMessage = errorMessage,
                tradeItemsLoading = tradeItemsLoading,
                onTradeItemsReload = onTradeItemsReload,
                onDeleteItem = onDeleteItem,
                sortByDescription = sortByDescription,
                sortByPrice = sortByPrice,
                onItemClick = onItemClick,
                currentUser = currentUser,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun TradeItemCard(
    tradeItem: TradeItem,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    canDelete: Boolean
    ) {
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(red = 90, green = 180, blue = 180),
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${tradeItem.description} ",
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${tradeItem.price} USD",
            )
            if (canDelete){
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Outlined.Delete, contentDescription = "Delete")
                }
            }
        }
    }
    if (showDialog) {
        AlertDialogExample(
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                onDelete()
                showDialog = false },
            dialogTitle = "Delete?",
            dialogText = "Are you sure?",
            icon = Icons.Outlined.Delete
        )
    }
}
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,   // bare en funktionstype
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = { Icon(icon, contentDescription = "Example Icon") },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Dismiss")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    isSignedIn: Boolean,
    onLogin: () -> Unit, onSignOut: () -> Unit,
) {
    TopAppBar(
        title = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "My Trading App",
                    fontSize = 20.sp
                )
            }
        },
        actions = {
            if (!isSignedIn){
                IconButton(onClick = { onLogin() }) {
                    Icon(Icons.AutoMirrored.Filled.Login, contentDescription = "Login")
                }
            }else{
                IconButton(onClick = { onSignOut() }) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                }
            }
        }
    )
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TradeItemListPanel(
    tradeItems: List<TradeItem>,
    modifier: Modifier = Modifier,
    errorMessage: String,
    onTradeItemsReload: () -> Unit = {},
    tradeItemsLoading: Boolean = false,
    sortByDescription: (Boolean) -> Unit,
    sortByPrice: (Boolean) -> Unit,
    onItemClick: (Int) -> Unit,
    onDeleteItem: (TradeItem) -> Unit,
    currentUser: FirebaseUser?,
    viewModel: TradeItemsViewModel,
) {
    Column(modifier = modifier.padding(8.dp)) {
        if (errorMessage.isNotEmpty()) {
            Text(text = "Problem: $errorMessage")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.searchQuery = it },
                label = { Text("Search by description or price") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { viewModel.filterByDescription() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Search")
            }
        }

        var sortDescriptionAscending by remember { mutableStateOf(true) }
        var sortPriceAscending by remember { mutableStateOf(true) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = {
                sortByDescription(sortDescriptionAscending)
                sortDescriptionAscending = !sortDescriptionAscending
            }) {
                Text(text = if (sortDescriptionAscending) "Description ↓" else "Description ↑")
            }
            OutlinedButton(onClick = {
                sortByPrice(sortPriceAscending)
                sortPriceAscending = !sortPriceAscending
            }) {
                Text(text = if (sortPriceAscending) "Price ↓" else "Price ↑")
            }
        }

        val orientation = LocalConfiguration.current.orientation
        val columns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2

        PullToRefreshBox(
            isRefreshing = tradeItemsLoading,
            onRefresh = { onTradeItemsReload() },
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(columns)) {
                items(tradeItems) { tradeItem ->
                    TradeItemCard(
                        tradeItem = tradeItem,
                        onClick = { onItemClick(tradeItem.id) },
                        onDelete = { onDeleteItem(tradeItem) },
                        canDelete = (tradeItem.sellerEmail == currentUser?.email)
                    )
                }
            }
        }
    }
}
