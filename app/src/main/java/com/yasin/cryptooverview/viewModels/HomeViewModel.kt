package com.yasin.cryptooverview.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val currencies = repository.currencies

    init {
        viewModelScope.launch {
           repository.refreshData()
        }
    }
}