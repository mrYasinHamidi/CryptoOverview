package com.yasin.cryptooverview.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CryptoCurrencyTable(
    @PrimaryKey(autoGenerate = true)
    val rank: Int?,
    val name: String,
    val symbol: String,
    val price: String,
    val high: String,
    val logoUrl: String,
    val marketCap: String,
    val maxSupply: String,
    val circulatingSupply: String,
    val priceChangeDaily: String,
    val priceChangeWeakly: String,
    val priceChangeMonthly: String,
    val priceChangeYearly: String

)