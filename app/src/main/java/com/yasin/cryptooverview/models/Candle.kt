package com.yasin.cryptooverview.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Candle(
    @Json(name = "close")
    val close: String?,
    @Json(name = "high")
    val high: String?,
    @Json(name = "low")
    val low: String?,
    @Json(name = "open")
    val `open`: String?,
    @Json(name = "period")
    val period: Long?,
    @Json(name = "volume")
    val volume: String?
)