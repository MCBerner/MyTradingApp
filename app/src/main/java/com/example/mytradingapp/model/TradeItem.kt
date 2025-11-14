package com.example.mytradingapp.model

import java.io.Serializable

data class TradeItem(
    val id: Int = 0,
    val description: String,
    val price: Int,
    val sellerEmail: String,
    val sellerPhone: String,
    val time: Long = System.currentTimeMillis()/1000,
    val pictureUrl: String = ""
) : Serializable {
    constructor(description: String, price: Int, sellerEmail: String, sellerPhone: String) :
            this(
                -1,
                description,
                price,
                sellerEmail,
                sellerPhone,
                time = System.currentTimeMillis ()/1000,
            )

    override fun toString(): String {
        return "$id $description, $price, $sellerEmail $sellerPhone"
    }
}