package com.example.mytradingapp.model

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mytradingapp.repository.MyTradingAppRepository

class TradeItemsViewModel : ViewModel() {
    private val repository = MyTradingAppRepository()
    val tradeItems: State<List<TradeItem>> = repository.tradeItems
    val errorMessage: State<String> = repository.errorMessage
    val isLoadingTradeItems: State<Boolean> = repository.isLoadingTradeItems

    var description by mutableStateOf("")
    var priceStr by mutableStateOf("")
    var sellerPhone by mutableStateOf("")
    var searchQuery by mutableStateOf("")




    init {
        reload()
    }

    fun reload() {
        repository.getTradeItems()
    }

    fun filterByDescription() {
        repository.filterByDescription(searchQuery)
    }

    fun sortByDescription(ascending: Boolean) {
        repository.sortByDescription(ascending)
    }

    fun sortByPrice(ascending: Boolean) {
        repository.sortByPrice(ascending)
    }

    fun getTradeItems() {
        repository.getTradeItems()
    }

    fun addTradeItem(tradeItem: TradeItem) {
        repository.addTradeItem(tradeItem)
    }

    fun deleteTradeItem(tradeItem: TradeItem) {
        repository.deleteTradeItem(tradeItem.id)
    }
}
