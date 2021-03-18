package com.yasin.cryptooverview.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(repository: Repository):ViewModel() {
    val cryptoCurrency = MutableLiveData<CryptoCurrency>()
}