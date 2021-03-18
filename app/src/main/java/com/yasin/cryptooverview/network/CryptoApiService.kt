package com.yasin.cryptooverview.network

import com.yasin.cryptooverview.CryptoConstants
import com.yasin.cryptooverview.CryptoConvert
import com.yasin.cryptooverview.CryptoInterval
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiService {
    @GET("currencies/ticker")
    fun getCryptoCurrencies(
        @Query("page") start: Int,
        @Query("per-page") limit: Int? = null,
        @Query("interval") interval: String = CryptoInterval.D1.interval,
        @Query("convert") convert: String = CryptoConvert.USD.convert,
        @Query("status") status: String = "active",
        @Query("key") apiKey: String = CryptoConstants.API_KEY
    ): Deferred<List<CryptoApiResponse>>


}
