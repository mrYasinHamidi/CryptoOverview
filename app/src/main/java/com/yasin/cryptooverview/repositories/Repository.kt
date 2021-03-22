package com.yasin.cryptooverview.repositories

import android.util.Log
import androidx.lifecycle.Transformations
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.arrayOfCryptoCurrencyTables
import com.yasin.cryptooverview.database.CryptoDatabase
import com.yasin.cryptooverview.listOfCryptoCurrencies
import com.yasin.cryptooverview.network.CryptoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(val service: CryptoApiService, val data: CryptoDatabase) {

    val currencies =
        Transformations.map(data.cryptoDao.getAllCrypto(), { it.listOfCryptoCurrencies() })


    suspend fun refreshData() = withContext(Dispatchers.IO) {
        try {
            val response = service.getCryptoCurrencies(1, 100).await()
            data.cryptoDao.insertAll(*response.arrayOfCryptoCurrencyTables())
            RequestStatus.Complete
        } catch (e: Exception) {
            RequestStatus.Error
        }
    }
}

