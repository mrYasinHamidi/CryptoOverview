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


    private val _currency = MutableLiveData<CryptoCurrency>()
    val currency: LiveData<CryptoCurrency>
        get() = _currency

    private val _chartData = MutableLiveData<List<Candle>>()
    val chartData: LiveData<List<Candle>>
        get() = _chartData

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus>
        get() = _requestStatus

    init {
        _requestStatus.value = RequestStatus.NONE
    }

    fun setCurrencyName(name: String) {
        viewModelScope.launch {
            _currency.postValue(repository.getCurrency(name))
        }
    }

    fun prepareChartData(
        base: String,
        target: String,
        exchange: String,
        interval: ChartDataInterval
    ) {
        _requestStatus.value = RequestStatus.Loading
        viewModelScope.launch {
            val candleList: List<Candle>? =
                repository.refreshChartData(base, target.toLowerCase(), exchange, interval) {
                    _requestStatus.postValue(it)
                }
            candleList?.let {
                _chartData.postValue(it)
            }
        }
    }

}