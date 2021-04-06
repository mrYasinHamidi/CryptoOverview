package com.yasin.cryptooverview.network


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yasin.cryptooverview.models.Candle
import com.yasin.cryptooverview.models.SearchResponse

@JsonClass(generateAdapter = true)
data class SearchApiResponse(
    @Json(name = "data")
    val `data`: List<SearchResponse>?,
    @Json(name = "timestamp")
    val timestamp: Long?
)