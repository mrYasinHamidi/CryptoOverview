package com.yasin.cryptooverview

import com.github.mikephil.charting.data.CandleEntry
import com.yasin.cryptooverview.database.CryptoCurrencyTable
import com.yasin.cryptooverview.models.Candle
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.network.CryptoApiResponse

class CryptoConstants {
    companion object {
        const val NOMICS_BASE_URL = "https://api.nomics.com/v1/"
        const val COIN_CAP_BASE_URL = "https://api.coincap.io/v2/"
        const val API_KEY = "98d7dff1d3aec8baf0bfe575fd80a8a0"
    }
}
//"https://api.nomics.com/v1/currencies/ticker?page=1&per-page=1000&interval=1D&convert=USD&status=active&key=98d7dff1d3aec8baf0bfe575fd80a8a0"

enum class ChartDataInterval(val interval: String) {
    M1("m1"), M5("m5"), M15("m15"),
    M30("m30"), H1("h1"), H2("h2"),
    H4("h4"), H8("h8"), H12("h12"),
    D1("d1"), W1("w1")
}

enum class CryptoConvert(val convert: String) { USD("USD"), EUR("EUR") }
enum class RequestStatus { NONE, Loading, Complete, Error }

fun List<CryptoApiResponse>.arrayOfCryptoCurrencyTables(): Array<CryptoCurrencyTable> {
    return map {
        CryptoCurrencyTable(
            rank = it.rank?.toInt(),
            name = it.name ?: "",
            symbol = it.symbol ?: "",
            price = it.price ?: "",
            high = it.logoUrl ?: "",
            logoUrl = it.logoUrl ?: "",
            marketCap = it.marketCap ?: "",
            maxSupply = it.maxSupply ?: "",
            circulatingSupply = it.circulatingSupply ?: "",
            priceChangeDaily = it.d1?.priceChange ?: "",
            priceChangeWeakly = it.d7?.priceChange ?: "",
            priceChangeMonthly = it.d30?.priceChange ?: "",
            priceChangeYearly = it.d365?.priceChange ?: ""
        )
    }.toTypedArray()
}

fun List<CryptoCurrencyTable>.listOfCryptoCurrencies(): List<CryptoCurrency> {
    return map {
        CryptoCurrency(
            rank = it.rank ?: 0,
            name = it.name,
            symbol = it.symbol,
            price = it.price,
            high = it.logoUrl,
            logoUrl = it.logoUrl,
            marketCap = it.marketCap,
            maxSupply = it.maxSupply,
            circulatingSupply = it.circulatingSupply,
            priceChangeDaily = it.priceChangeDaily,
            priceChangeWeakly = it.priceChangeWeakly,
            priceChangeMonthly = it.priceChangeMonthly,
            priceChangeYearly = it.priceChangeYearly
        )
    }
}

fun <E> List<E>.getRange(range: IntProgression): MutableList<E> {
    val a = mutableListOf<E>()

    for ((i, j) in this.withIndex())
        if (i in range)
            a.add(j)

    return a
}

fun List<Candle>.toCandleEntry() = if (isNotEmpty()) mapIndexed { index, candle ->
    CandleEntry(
        index.toFloat(),
        candle.high?.toFloat() ?: 0F,
        candle.low?.toFloat() ?: 0F,
        candle.open?.toFloat() ?: 0F,
        candle.close?.toFloat() ?: 0F
    )
} else null


