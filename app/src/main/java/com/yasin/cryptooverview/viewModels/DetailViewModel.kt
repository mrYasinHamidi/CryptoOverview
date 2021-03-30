package com.yasin.cryptooverview.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasin.cryptooverview.ChartDataInterval
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.models.Candle
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.repositories.ChartRepository
import com.yasin.cryptooverview.repositories.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: ChartRepository) : ViewModel() {

    val cryptoCurrency = MutableLiveData<CryptoCurrency>()

    private val _chartData = MutableLiveData<List<Candle>>()
    val chartData: LiveData<List<Candle>>
        get() = _chartData

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus>
        get() = _requestStatus


    fun getChartData(interval: ChartDataInterval) {
        Log.i("aaaDetaiViewModel","getData")
        _requestStatus.value = RequestStatus.Loading
        viewModelScope.launch {
            repository.getChartData(
                "tether",
                cryptoCurrency.value?.name?.toLowerCase()?.replace(" ", "-") ?: "",
                "binance",
                interval
            ) { _requestStatus.postValue(it) }?.let {
                _chartData.postValue(it)
            }

        }
    }

    fun getCandle(x: Float) =
        try {
            _chartData.value?.get(x.toInt())
        } catch (e: Exception) {
            null
        }


}