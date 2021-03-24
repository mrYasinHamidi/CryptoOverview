package com.yasin.cryptooverview.destinations

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.google.android.material.transition.MaterialContainerTransform
import com.yasin.cryptooverview.R
import com.yasin.cryptooverview.databinding.FragmentDetailBinding
import com.yasin.cryptooverview.viewModels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    val viewModel: DetailViewModel by viewModels()
    val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        sharedElementEnterTransition = MaterialContainerTransform()
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 300L
            scrimColor = Color.TRANSPARENT
//            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
            setAllContainerColors(Color.WHITE)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        ViewCompat.setTransitionName(binding.layoutMainDetail, "yasin")
        binding.viewModel = viewModel
        viewModel.cryptoCurrency.value = args.CryptoCurrency
        viewModel.getChartData()
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chartConfig(binding.detailChart)

        binding.detailCloseIcon.setOnClickListener {
            findNavController().navigateUp()
        }
        viewModel._chartData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                val set1 = CandleDataSet(it.mapIndexed { index, candle ->
                    CandleEntry(
                        index.toFloat(),
                        candle.high?.toFloat() ?: 0F,
                        candle.low?.toFloat() ?: 0F,
                        candle.open?.toFloat() ?: 0F,
                        candle.close?.toFloat() ?: 0F
                    )
                }.reversed().subList(0, 20).reversed(), "data set")

                set1.setDrawIcons(false)
                set1.axisDependency = AxisDependency.LEFT
                set1.shadowWidth = 0.7f
                set1.decreasingColor = Color.RED
                set1.decreasingPaintStyle = Paint.Style.FILL
                set1.increasingColor = Color.GREEN
                set1.increasingPaintStyle = Paint.Style.FILL
                set1.neutralColor = Color.BLUE
                set1.shadowColorSameAsCandle = true

                binding.detailChart.data = CandleData(set1)
                binding.detailChart.invalidate()
            }
        })
    }

    private fun chartConfig(chart: CandleStickChart) {
        chart.setBackgroundColor(Color.WHITE)

        chart.getDescription().setEnabled(false)

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(true)

        chart.setDrawGridBackground(false)


        val xAxis: XAxis = chart.getXAxis()
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        val leftAxis: YAxis = chart.getAxisLeft()
//        leftAxis.setEnabled(false);
        //        leftAxis.setEnabled(false);
        leftAxis.setLabelCount(7, false)
        leftAxis.setDrawGridLines(false)
        leftAxis.setDrawAxisLine(false)

        val rightAxis: YAxis = chart.getAxisRight()
        rightAxis.isEnabled = false
//        rightAxis.setStartAtZero(false);

        // setting data
        //        rightAxis.setStartAtZero(false);

        chart.getLegend().setEnabled(false)
    }
}