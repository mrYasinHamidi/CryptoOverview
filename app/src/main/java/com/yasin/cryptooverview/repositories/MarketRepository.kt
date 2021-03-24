package com.yasin.cryptooverview.repositories

import android.util.Log
import androidx.lifecycle.Transformations
import com.yasin.cryptooverview.CryptoConvert
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.arrayOfCryptoCurrencyTables
import com.yasin.cryptooverview.database.CryptoDatabase
import com.yasin.cryptooverview.di.NetworkModule
import com.yasin.cryptooverview.listOfCryptoCurrencies
import com.yasin.cryptooverview.network.CryptoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarketRepository @Inject constructor(
    @NetworkModule.Nomics val service: CryptoApiService,
    val data: CryptoDatabase
) {

    val currencies =
        Transformations.map(data.cryptoDao.getAllCrypto(), { it.listOfCryptoCurrencies() })


    suspend fun refreshData() = withContext(Dispatchers.IO) {
        try {
            val response = service.getCryptoCurrencies(
                start = 1,
                limit = 100,
                convert = CryptoConvert.USD.convert
            ).await()
            data.cryptoDao.insertAll(*response.arrayOfCryptoCurrencyTables())
            RequestStatus.Complete
        } catch (e: Exception) {
            RequestStatus.Error
        }
    }
}

