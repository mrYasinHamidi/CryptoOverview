package com.yasin.cryptooverview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yasin.cryptooverview.R
import com.yasin.cryptooverview.customs.CryptoRecyclerViewOnClick
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.databinding.MainListItemBinding

class CryptoRecyclerviewViewHolder(private val binding: MainListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CryptoCurrency, listener: CryptoRecyclerViewOnClick) {
        binding.cryptoCurrency = item
        ViewCompat.setTransitionName(binding.listCardView, "yasin${item.rank}")
        binding.listCardView.setOnClickListener(listener.onClick(item))
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CryptoRecyclerviewViewHolder {
//            val binding = MainListItemBinding.inflate(LayoutInflater.from(parent.context))
            val binding = DataBindingUtil.inflate<MainListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.main_list_item, parent, false
            )
            return CryptoRecyclerviewViewHolder(binding)
        }
    }
}