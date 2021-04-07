package com.yasin.cryptooverview.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yasin.cryptooverview.ChartDataInterval
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.database.CryptoDatabase
import com.yasin.cryptooverview.di.NetworkModule
import com.yasin.cryptooverview.models.Candle
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.network.CryptoApiService
import com.yasin.cryptooverview.toListOfCryptoCurrencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ChartRepository @Inject constructor(
    @NetworkModule.CoinCap val service: CryptoApiService,
    val data: CryptoDatabase
) {


    suspend fun getCurrency(name: String) =
        withContext(Dispatchers.IO) {
            val cryptoCurrencyTable = data.cryptoDao.get(name)
            CryptoCurrency(
                rank = cryptoCurrencyTable.rank ?: 0,
                name = cryptoCurrencyTable.name,
                symbol = cryptoCurrencyTable.symbol,
                price = cryptoCurrencyTable.price,
                high = cryptoCurrencyTable.logoUrl,
                logoUrl = cryptoCurrencyTable.logoUrl,
                marketCap = cryptoCurrencyTable.marketCap,
                maxSupply = cryptoCurrencyTable.maxSupply,
                circulatingSupply = cryptoCurrencyTable.circulatingSupply,
                priceChangeDaily = cryptoCurrencyTable.priceChangeDaily,
                priceChangeWeakly = cryptoCurrencyTable.priceChangeWeakly,
                priceChangeMonthly = cryptoCurrencyTable.priceChangeMonthly,
                priceChangeYearly = cryptoCurrencyTable.priceChangeYearly
            )
        }

    suspend fun refreshChartData(
        baseAssetId: String,
        targetAssetId: String,
        targetExchangeId: String,
        interval: ChartDataInterval = ChartDataInterval.D1,
        status: (RequestStatus) -> Unit
    ) =
        withContext(Dispatchers.IO) {
            var candleList: List<Candle>? = null
            try {
                candleList = service.getChartDataForCryptoCurrencies(
                    exchange = targetExchangeId,
                    interval = interval.interval,
                    targetAsset = targetAssetId,
                    baseAsset = baseAssetId
                ).await().data
                status(RequestStatus.Complete)
            } catch (e: Exception) {
                status(RequestStatus.Error)
            }
            candleList
        }


    suspend fun search(
        name: String,
        status: (RequestStatus) -> Unit
    ) =
        withContext(Dispatchers.IO) {
            var searchResponse: List<CryptoCurrency>? = null
            try {
                searchResponse = service.search(name).await().data?.toListOfCryptoCurrencies()
                status(RequestStatus.Complete)
            } catch (e: Exception) {
                status(RequestStatus.Error)
            }
            return@withContext searchResponse
        }


}