package com.yasin.cryptooverview.destinations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.yasin.cryptooverview.R
import com.yasin.cryptooverview.adapters.CryptoRecyclerViewAdapter
import com.yasin.cryptooverview.customs.CryptoRecyclerViewOnClick
import com.yasin.cryptooverview.databinding.FragmentHomeBinding
import com.yasin.cryptooverview.databinding.MainListItemBinding
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        exitTransition = Hold()
        exitTransition = MaterialElevationScale(false).apply {
            duration = 300L
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 300L
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }


        val adapter = CryptoRecyclerViewAdapter(CryptoRecyclerViewOnClick {
            createOnClickListener(it)
        })

        binding.homeRecyclerView.adapter = adapter


        viewModel.currencies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })


    }
}

private fun createOnClickListener(
    cryptoCurrency: CryptoCurrency
): View.OnClickListener {
    return View.OnClickListener {
        val directions = HomeFragmentDirections.actionHomeDestinationToDetail(cryptoCurrency)
        val extras = FragmentNavigatorExtras(it to "yasin")
        it.findNavController().navigate(directions, extras)
    }
}