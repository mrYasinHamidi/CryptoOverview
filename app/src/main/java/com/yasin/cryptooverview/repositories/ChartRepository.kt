package com.yasin.cryptooverview.repositories

import android.util.Log
import com.yasin.cryptooverview.ChartDataInterval
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.di.NetworkModule
import com.yasin.cryptooverview.models.Candle
import com.yasin.cryptooverview.network.CryptoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ChartRepository @Inject constructor(
    @NetworkModule.CoinCap val service: CryptoApiService
) {

    suspend fun getChartData(
        baseAssetId: String,
        targetAssetId: String,
        targetExchangeId: String,
        interval: ChartDataInterval = ChartDataInterval.D1,
        status: (RequestStatus) -> Unit
    ) =
        withContext(Dispatchers.IO) {
            var chartData: List<Candle>? = null
            try {
                chartData = service.getChartDataForCryptoCurrencies(
                    exchange = targetExchangeId,
                    interval = interval.interval,
                    targetAsset = targetAssetId,
                    baseAsset = baseAssetId
                ).await().data
                status(RequestStatus.Complete)
            } catch (e: Exception) {
                status(RequestStatus.Error)
                Log.i("aaaChartRepository",e.message.toString())
            }
            return@withContext chartData
        }


}
