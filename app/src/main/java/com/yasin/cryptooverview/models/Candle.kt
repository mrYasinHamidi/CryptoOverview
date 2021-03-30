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
){
    override fun toString(): String {
//        return "open : $open high : $high low : $low close : $close"
        return String.format("open : %.2f high : %.2f low : %.2f close : %.2f",open?.toFloat(),high?.toFloat(),low?.toFloat(),close?.toFloat(),)
    }
}