package com.example.mytradingapp.model

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.mytradingapp.repository.MyTradingAppRepository

class TradeItemsViewModel : ViewModel() {
    private val repository = MyTradingAppRepository()
    val tradeItems: State<List<TradeItem>> = repository.tradeItems
    val errorMessage: State<String> = repository.errorMessage
    val isLoadingTradeItems: State<Boolean> = repository.isLoadingTradeItems

    //val tradeItemsLiveData: LiveData<List<TradeItem>> = repository.tradeItemsLiveData
    //val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    //val tradeItemLiveData = MutableLiveData<TradeItem>()

    init {
        reload()
    }

    fun reload() {
        repository.getTradeItems()
    }

    fun filterByDescription(descriptionFragment: String) {
        repository.filterByDescription(descriptionFragment)
    }

    fun sortByDescription(ascending: Boolean) {
        repository.sortByDescription(ascending)
    }

    fun sortByPrice(ascending: Boolean) {
        repository.sortByPrice(ascending)
    }

    fun getTradeItems(){
        repository.getTradeItems()
    }

    fun addTradeItem(tradeItem: TradeItem) {
        repository.addTradeItem(tradeItem)
    }

    fun deleteTradeItem(tradeItem: TradeItem) {
        repository.deleteTradeItem(tradeItem.id)
    }
}
