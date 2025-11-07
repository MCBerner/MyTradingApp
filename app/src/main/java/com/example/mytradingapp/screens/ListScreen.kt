@file:Suppress("SpellCheckingInspection", "DEPRECATION")

package com.example.mytradingapp.screens

import android.R.attr.onClick
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytradingapp.model.TradeItem
import com.example.mytradingapp.model.TradeItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    viewModel: TradeItemsViewModel = viewModel(),
    onProfileClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onLogRegClick: () -> Unit,
    onAddClick: () -> Unit = {}
) {
    val tradeItems by viewModel.tradeItemsLiveData.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessageLiveData.observeAsState("")

    Scaffold(
        topBar = {
            MyTopBar(
                onLogRegClick = onLogRegClick)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddClick()}) {
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
                onTradeItemsReload = { viewModel.reload() },
                tradeItemsLoading = false,
                onSearch = viewModel::search,
                sortByDescription = viewModel::sortByDescription,
                sortByPrice = viewModel::sortByPrice,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun TradeItemCard(
    item: TradeItem,
    onClick: () -> Unit
) {
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
                text = "${item.description} ",
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${item.price} USD",
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    onLogRegClick: () -> Unit) {
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
                Button(
                    onClick = onLogRegClick,
                ) {
                    Text("Log/Reg")
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
    onSearch: (String) -> Unit,
    sortByDescription: (Boolean) -> Unit,
    sortByPrice: (Boolean) -> Unit,
    onItemClick: (Int) -> Unit
) {

    Column(modifier = modifier.padding(8.dp)) {
        if (errorMessage.isNotEmpty()) {
            Text(text = "Problem: $errorMessage")
        }
        var searchQuery by remember { mutableStateOf("") }

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by description or price") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { onSearch(searchQuery) },
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
                        item = tradeItem,
                        onClick = { onItemClick(tradeItem.id) }
                    )
                }
            }
        }
    }
}
