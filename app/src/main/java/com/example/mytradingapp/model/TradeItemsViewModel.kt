package com.example.mytradingapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytradingapp.repository.MyTradingAppRepository

class TradeItemsViewModel : ViewModel() {
    private val repository = MyTradingAppRepository()
    val tradeItemsLiveData: LiveData<List<TradeItem>> = repository.tradeItemsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val itemLiveData = MutableLiveData<TradeItem>()

    init {
        reload()
    }
    fun reload() {
        repository.getTradingItems()
    }
    fun search(query: String) {
        repository.search(query)
    }
    fun sortByDescription(ascending: Boolean) {
        repository.sortByDescription(ascending)
    }
    fun sortByPrice(ascending: Boolean) {
        repository.sortByPrice(ascending)
    }

    operator fun get(index: Int): TradeItem? {
        return tradeItemsLiveData.value?.get(index)
    }

    /*fun getById(id: Int) {
        repository.getById(id){ item ->
            itemLiveData.postValue(item)
        }

    }*/
}