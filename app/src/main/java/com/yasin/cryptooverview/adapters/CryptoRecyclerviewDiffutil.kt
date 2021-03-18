package com.yasin.cryptooverview.adapters

import androidx.recyclerview.widget.DiffUtil
import com.yasin.cryptooverview.models.CryptoCurrency

class CryptoRecyclerviewDiffutil : DiffUtil.ItemCallback<CryptoCurrency>() {
    override fun areItemsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency): Boolean {
        return oldItem.rank == newItem.rank
    }

    override fun areContentsTheSame(oldItem: CryptoCurrency, newItem: CryptoCurrency): Boolean {
        return oldItem == newItem
    }
}