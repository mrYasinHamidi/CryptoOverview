package com.yasin.cryptooverview.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.yasin.cryptooverview.customs.CryptoRecyclerViewOnClick
import com.yasin.cryptooverview.models.CryptoCurrency

class CryptoRecyclerViewAdapter(private val listener: CryptoRecyclerViewOnClick) :
    ListAdapter<CryptoCurrency, CryptoRecyclerviewViewHolder>(CryptoRecyclerviewDiffutil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CryptoRecyclerviewViewHolder {
        return CryptoRecyclerviewViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CryptoRecyclerviewViewHolder, position: Int) {
        holder.bind(getItem(position),listener)
    }
}