package com.yasin.cryptooverview.network

import com.yasin.cryptooverview.ChartDataInterval
import com.yasin.cryptooverview.CryptoConstants
import com.yasin.cryptooverview.CryptoConvert
import com.yasin.cryptooverview.models.Candle
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiService {
    @GET("currencies/ticker")
    fun getCryptoCurrencies(
        @Query("page") start: Int,
        @Query("per-page") limit: Int? = null,
        @Query("convert") convert: String = CryptoConvert.USD.convert,
        @Query("status") status: String = "active",
        @Query("key") apiKey: String = CryptoConstants.API_KEY
    ): Deferred<List<CryptoApiResponse>>

    @GET("candles")
    fun getChartDataForCryptoCurrencies(
        @Query("exchange") exchange: String,
        @Query("interval") interval: String = ChartDataInterval.D1.interval,
        @Query("baseId") targetAsset: String ,
        @Query("quoteId") baseAsset: String ,
    ): Deferred<ChartApiResponse>


}
