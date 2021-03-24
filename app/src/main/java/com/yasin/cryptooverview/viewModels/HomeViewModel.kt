package com.yasin.cryptooverview.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.repositories.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val marketRepository: MarketRepository,
) : ViewModel() {

    val currencies = marketRepository.currencies

    private val _status = MutableLiveData<RequestStatus>()
    val status: LiveData<RequestStatus>
        get() = _status

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        getData()
    }

    fun getData() {
        _status.value = RequestStatus.Loading
        viewModelScope.launch {
            _status.postValue(marketRepository.refreshData())
        }
    }
}