package com.yasin.cryptooverview.destinations

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.fede987.statusbaralert.StatusBarAlert
import com.fede987.statusbaralert.StatusBarAlertView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialElevationScale
import com.yasin.cryptooverview.R
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.adapters.CryptoRecyclerViewAdapter
import com.yasin.cryptooverview.customs.CryptoRecyclerViewOnClick
import com.yasin.cryptooverview.databinding.FragmentHomeBinding
import com.yasin.cryptooverview.models.CryptoCurrency
import com.yasin.cryptooverview.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private var alert: StatusBarAlertView? = null

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

        binding.list.adapter = adapter

        viewModel.currencies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.swipeLayout.setOnRefreshListener {
            viewModel.getData()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            when (it) {
                RequestStatus.Loading -> {
                    alert = createStatusBarAlert(false, true, "Updating... ")
                }
                RequestStatus.Complete -> {
                    alert = createStatusBarAlert(true, false, "Complete")
                    viewModel.refreshStatus()
                }
                RequestStatus.Error -> {
                    alert = createStatusBarAlert(true, false, "TryAgain!!!")
                    viewModel.refreshStatus()
                }
                else -> {

                }
            }
            binding.swipeLayout.isRefreshing = false

        })

    }


    private fun createStatusBarAlert(hide: Boolean, progress: Boolean, text: String) =
        StatusBarAlert.Builder(requireActivity())
            .autoHide(hide)
            .withAlertColor(R.color.MainBackground)
            .withText(text)
            .showProgress(progress)
            .withIndeterminateProgressBarColor(R.color.colorSecondary)
            .withTextColor(R.color.colorOnPrimary)
            .build()


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