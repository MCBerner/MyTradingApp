package com.example.mytradingapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mytradingapp.model.TradeItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyTradingAppRepository {
    private val baseUrl = "https://anbo-salesitems.azurewebsites.net/api/"
    private var allTradeItems: List<TradeItem> = emptyList()
    private val myTradingAppService: MyTradingAppService
    val tradeItemsLiveData: MutableLiveData<List<TradeItem>> = MutableLiveData<List<TradeItem>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()


    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        myTradingAppService = build.create(MyTradingAppService::class.java)
        getTradingItems()
    }

    fun getTradingItems(onResult: (List<TradeItem>) -> Unit = {}) {
        myTradingAppService.getAllTradeItems().enqueue(object : Callback<List<TradeItem>> {
            override fun onResponse(
                call: Call<List<TradeItem>>,
                response: Response<List<TradeItem>>,
            ) {
                if (response.isSuccessful) {
                    val items = response.body().orEmpty()
                    allTradeItems = items
                    tradeItemsLiveData.postValue(items)
                    errorMessageLiveData.postValue("")
                    onResult(items)
                } else {
                    val message = "${response.code()} ${response.message()}"
                    errorMessageLiveData.postValue(message)
                    Log.e("API", message)
                    onResult(emptyList())
                }
            }

            override fun onFailure(call: Call<List<TradeItem>>, t: Throwable) {
                val msg = t.message ?: "Unknown error"
                errorMessageLiveData.postValue(msg)
                Log.e("API", msg)
                onResult(emptyList())
            }
        })
    }

    fun search(query: String) {
        if (query.isBlank()) {
            tradeItemsLiveData.postValue(allTradeItems)
        } else {
            val filtered = allTradeItems.filter {
                it.description.contains(query, ignoreCase = true) ||
                        it.price.toString().contains(query)
            }
            tradeItemsLiveData.postValue(filtered)
        }
    }

    fun sortByDescription(ascending: Boolean) {
        val currentList = tradeItemsLiveData.value ?: return
        val sortedList = if (ascending) {
            currentList.sortedBy { it.description }
        } else {
            currentList.sortedByDescending { it.description }
        }
        tradeItemsLiveData.postValue(sortedList)
    }

    fun sortByPrice(ascending: Boolean) {
        val currentList = tradeItemsLiveData.value ?: return
        val sortedList = if (ascending) {
            currentList.sortedBy { it.price }
        } else {
            currentList.sortedByDescending { it.price }
        }
        tradeItemsLiveData.postValue(sortedList)
    }
}