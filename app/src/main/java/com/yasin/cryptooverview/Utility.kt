package com.yasin.cryptooverview

import com.yasin.cryptooverview.database.CryptoCurrencyTable
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.network.CryptoApiResponse

class CryptoConstants {
    companion object {
        const val BASE_URL = "https://api.nomics.com/v1/"
        const val API_KEY = "98d7dff1d3aec8baf0bfe575fd80a8a0"
    }
}
//"https://api.nomics.com/v1/currencies/ticker?page=1&per-page=1000&interval=1D&convert=USD&status=active&key=98d7dff1d3aec8baf0bfe575fd80a8a0"

enum class CryptoInterval(val interval: String) { H1("1h"), D1("1d"), D7("7d"), D30("30d") }
enum class CryptoConvert(val convert: String) { USD("USD"), EUR("EUR") }

fun List<CryptoApiResponse>.arrayOfCryptoCurrencyTables(): Array<CryptoCurrencyTable> {
    return map {
        CryptoCurrencyTable(
            rank = it.rank?.toInt(),
            name = it.name?:"",
            symbol =  it.symbol?:"",
            price = it.price?:"",
            high = it.logoUrl?:"",
            logoUrl = it.logoUrl?:"",
            marketCap = it.marketCap?:"",
            maxSupply = it.maxSupply?:"",
            circulatingSupply = it.circulatingSupply?:""
        )
    }.toTypedArray()
}

fun List<CryptoCurrencyTable>.listOfCryptoCurrencies(): List<CryptoCurrency> {
    return map {
        CryptoCurrency(
            rank = it.rank?:0,
            name = it.name,
            symbol =  it.symbol,
            price = it.price,
            high = it.logoUrl,
            logoUrl = it.logoUrl,
            marketCap = it.marketCap,
            maxSupply = it.maxSupply,
            circulatingSupply = it.circulatingSupply
        )
    }
}
