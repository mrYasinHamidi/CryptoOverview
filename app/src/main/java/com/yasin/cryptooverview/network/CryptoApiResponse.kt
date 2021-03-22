package com.yasin.cryptooverview.network


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptoApiResponse(
    @Json(name = "circulating_supply")
    val circulatingSupply: String?,
    @Json(name = "currency")
    val currency: String?,
    @Json(name = "1d")
    val d1: D?,
    @Json(name = "7d")
    val d7: DX?,
    @Json(name = "30d")
    val d30: DXX?,
    @Json(name = "365d")
    val d365: DXXX?,
    @Json(name = "first_candle")
    val firstCandle: String?,
    @Json(name = "first_order_book")
    val firstOrderBook: String?,
    @Json(name = "first_trade")
    val firstTrade: String?,
    @Json(name = "high")
    val high: String?,
    @Json(name = "high_timestamp")
    val highTimestamp: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "logo_url")
    val logoUrl: String?,
    @Json(name = "market_cap")
    val marketCap: String?,
    @Json(name = "max_supply")
    val maxSupply: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "num_exchanges")
    val numExchanges: String?,
    @Json(name = "num_pairs")
    val numPairs: String?,
    @Json(name = "num_pairs_unmapped")
    val numPairsUnmapped: String?,
    @Json(name = "price")
    val price: String?,
    @Json(name = "price_date")
    val priceDate: String?,
    @Json(name = "price_timestamp")
    val priceTimestamp: String?,
    @Json(name = "rank")
    val rank: String?,
    @Json(name = "rank_delta")
    val rankDelta: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "symbol")
    val symbol: String?,
    @Json(name = "ytd")
    val ytd: Ytd?
)