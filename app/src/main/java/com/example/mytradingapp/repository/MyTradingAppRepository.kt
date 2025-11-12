package com.example.mytradingapp.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mytradingapp.model.TradeItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyTradingAppRepository {
    private val baseUrl = "https://anbo-salesitems.azurewebsites.net/api/"
    private val myTradingAppService: MyTradingAppService
    val tradeItems: MutableState<List<TradeItem>> = mutableStateOf(listOf())
    val isLoadingTradeItems = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    //val allTradeItems: MutableState<List<TradeItem>> = mutableStateOf(listOf())


    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        myTradingAppService = build.create(MyTradingAppService::class.java)
        getTradeItems()
    }

    fun getTradeItems() {
        isLoadingTradeItems.value = true
        myTradingAppService.getAllTradeItems().enqueue(object : Callback<List<TradeItem>> {
            override fun onResponse(
                call: Call<List<TradeItem>>,
                response: Response<List<TradeItem>>,
            ) {
                isLoadingTradeItems.value = false
                if (response.isSuccessful) {
                    val tradeItemList: List<TradeItem>? = response.body()
                    tradeItems.value = tradeItemList ?: emptyList()
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + "" + response.message()
                    errorMessage.value = message
                    Log.e("APPEL", message)
                }

            }

            override fun onFailure(call: Call<List<TradeItem>>, t: Throwable) {
                isLoadingTradeItems.value = false
                val message = t.message ?: "No connection to backend"
                errorMessage.value = message
                Log.e("APPEL", message)

            }


        })
    }

    fun addTradeItem(tradeItem: TradeItem) {
        myTradingAppService.addTradeItem(tradeItem).enqueue(object : Callback<TradeItem> {
            override fun onResponse(
                call: Call<TradeItem>,
                response: Response<TradeItem>,
            ) {
                if (response.isSuccessful) {
                    Log.d("APPEL", "Trade item added:" + response.body())
                    getTradeItems()
                    errorMessage.value = "Trade item added"
                } else {
                    val message = response.code().toString() + "" + response.message()
                    errorMessage.value = message
                    Log.e("APPEL", message)
                }
            }

            override fun onFailure(call: Call<TradeItem?>, t: Throwable) {
                val message = t.message ?: "No connection to backend"
                errorMessage.value = message
                Log.e("APPEL", message)
            }
        })
    }
    fun deleteTradeItem(id: Int) {
        Log.d("APPEL", "delete trade item: $id")
        myTradingAppService.deleteTradeItem(id).enqueue(object : Callback<TradeItem> {
            override fun onResponse(call: Call<TradeItem?>, response: Response<TradeItem>) {
                if (response.isSuccessful) {
                    Log.d("APPEL", "Trade item deleted:" + response.body())
                    errorMessage.value = ""
                    getTradeItems()

                }else{
                    val message = response.code().toString() + "" + response.message()
                    errorMessage.value = message
                    Log.e("APPEL" ,"Not deleted:, $message")
                }

            }
            override fun onFailure(call: Call<TradeItem?>, t: Throwable) {
                val message = t.message ?: "No connection to backend"
                errorMessage.value = message
                Log.e("APPEL", message)
            }
        })


    }
    fun filterByDescription(descriptionFragment: String) {
        val filtered = tradeItems.value.filter {
            it.description.contains(descriptionFragment, ignoreCase = true)
        }
        tradeItems.value = filtered
    }

    fun sortByDescription(ascending: Boolean) {
        Log.d("APPEL", "sort by description")
        if (ascending) tradeItems.value = tradeItems.value.sortedBy { it.description }
        else {
            tradeItems.value = tradeItems.value.sortedByDescending { it.description }
        }
    }

    fun sortByPrice(ascending: Boolean) {
        Log.d("APPEL", "sort by price")
        tradeItems.value = if (ascending) {
            tradeItems.value.sortedBy { it.price }
        } else {
            tradeItems.value.sortedByDescending { it.price }
        }


    }

}