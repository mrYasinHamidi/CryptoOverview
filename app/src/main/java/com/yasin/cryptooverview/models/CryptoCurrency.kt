package com.yasin.cryptooverview.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CryptoCurrency(
    val rank: Int,
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
):Parcelable
