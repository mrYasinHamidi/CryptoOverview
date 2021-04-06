package com.yasin.cryptooverview.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "changePercent24Hr")
    val changePercent24Hr: String?,
    @Json(name = "explorer")
    val explorer: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "marketCapUsd")
    val marketCapUsd: String?,
    @Json(name = "maxSupply")
    val maxSupply: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "priceUsd")
    val priceUsd: String?,
    @Json(name = "rank")
    val rank: String?,
    @Json(name = "supply")
    val supply: String?,
    @Json(name = "symbol")
    val symbol: String?,
    @Json(name = "volumeUsd24Hr")
    val volumeUsd24Hr: String?,
    @Json(name = "vwap24Hr")
    val vwap24Hr: String?
)