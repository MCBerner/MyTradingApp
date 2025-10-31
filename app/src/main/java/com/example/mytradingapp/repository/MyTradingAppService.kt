package com.example.mytradingapp.repository

import retrofit2.Call
import com.example.mytradingapp.model.TradeItem
import retrofit2.http.GET
import retrofit2.http.Path

interface MyTradingAppService {
    @GET("SalesItems")
    fun getAllTradeItems(): Call<List<TradeItem>>

    @GET("SalesItems/{id}")
    fun getTradeItemById(@Path("id") id: Int): Call<List<TradeItem>>
}