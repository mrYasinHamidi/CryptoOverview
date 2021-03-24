package com.yasin.cryptooverview.network


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yasin.cryptooverview.models.Candle

@JsonClass(generateAdapter = true)
data class ChartApiResponse(
    @Json(name = "data")
    val `data`: List<Candle>?,
    @Json(name = "timestamp")
    val timestamp: Long?
)