package com.yasin.cryptooverview.destinations

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialContainerTransform
import com.yasin.cryptooverview.R
import com.yasin.cryptooverview.adapters.CryptoRecyclerViewAdapter
import com.yasin.cryptooverview.customs.CryptoRecyclerViewOnClick
import com.yasin.cryptooverview.databinding.FragmentSearchBinding
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.viewModels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<FloatingActionButton>(R.id.fab).apply {
            setImageResource(R.drawable.ic_arrow_down)
            setOnClickListener {
                requireActivity().findNavController(R.id.nav_host_fragment).navigateUp()
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        val adapter =
            CryptoRecyclerViewAdapter(CryptoRecyclerViewOnClick { createOnClickListener(it) })

        binding.searchList.adapter = adapter
        viewModel.SearchResponse.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }

    private fun createOnClickListener(
        cryptoCurrency: CryptoCurrency
    ): View.OnClickListener {
        return View.OnClickListener {
            val directions = SearchFragmentDirections.actionSearchFragmentToDetail(cryptoCurrency)
            val extras = FragmentNavigatorExtras(it to "yasin")
            it.findNavController().navigate(directions, extras)
        }
    }
}