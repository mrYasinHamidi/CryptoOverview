package com.yasin.cryptooverview.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.models.SearchResponse
import com.yasin.cryptooverview.repositories.ChartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: ChartRepository) : ViewModel() {

    private val response = MutableLiveData<List<CryptoCurrency>>()
    val SearchResponse: LiveData<List<CryptoCurrency>>
        get() = response

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus>
        get() = _requestStatus


    fun search(name: String) {
        _requestStatus.value = RequestStatus.Loading
        viewModelScope.launch {
            repository.search(
                name,
            ) { _requestStatus.postValue(it) }?.let {
                response.postValue(it)
            }

        }
    }


}