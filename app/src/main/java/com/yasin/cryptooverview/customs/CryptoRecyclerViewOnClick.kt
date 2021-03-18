package com.yasin.cryptooverview.customs

import android.view.View
import com.yasin.cryptooverview.databinding.MainListItemBinding
import com.yasin.cryptooverview.models.CryptoCurrency

class CryptoRecyclerViewOnClick(val a: (CryptoCurrency) -> View.OnClickListener) {
    fun onClick(item: CryptoCurrency) = a(item)

}