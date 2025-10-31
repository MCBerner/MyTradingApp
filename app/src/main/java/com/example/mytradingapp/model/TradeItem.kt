package com.example.mytradingapp.model

import java.io.Serializable

data class TradeItem(
    val id: Int,
    val description: String,
    val price: Double,
    val sellerEmail: String,
    val sellerPhone: String,
) : Serializable {
    constructor(description: String,
                price: Double,
                sellerEmail: String,
                sellerPhone: String,) : this(0, description, price, sellerEmail, sellerPhone)

    override fun toString(): String {
        return "$id $description, $price, $sellerEmail $sellerPhone"
    }
}