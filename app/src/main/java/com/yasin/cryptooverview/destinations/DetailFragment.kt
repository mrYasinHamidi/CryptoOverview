package com.yasin.cryptooverview.destinations

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialContainerTransform
import com.yasin.cryptooverview.ChartDataInterval
import com.yasin.cryptooverview.R
import com.yasin.cryptooverview.databinding.FragmentDetailBinding
import com.yasin.cryptooverview.listeners.SwitcherItemsClickListener
import com.yasin.cryptooverview.models.Candle
import com.yasin.cryptooverview.viewModels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.max

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var chart: CandleStickChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 300L
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(Color.TRANSPARENT)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        activity?.findViewById<BottomNavigationView>(R.id.main_bottom_navigation)?.visibility = View.GONE

        ViewCompat.setTransitionName(binding.layoutMainDetail, "yasin")

        binding.viewModel = viewModel

        viewModel.cryptoCurrency.value = args.CryptoCurrency

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chart = binding.detailChart

        chartConfig(chart)

        viewModel.getChartData(ChartDataInterval.D1)

        binding.switcher.addSwitcherItemClickListener(
            SwitcherItemsClickListener(
                clickOn1h = { viewModel.getChartData(ChartDataInterval.H1) },
                clickOn2h = { viewModel.getChartData(ChartDataInterval.H2) },
                clickOn4h = { viewModel.getChartData(ChartDataInterval.H4) },
                clickOn8h = { viewModel.getChartData(ChartDataInterval.H8) },
                clickOn1d = { viewModel.getChartData(ChartDataInterval.D1) },
                clickOn1w = { viewModel.getChartData(ChartDataInterval.W1) })
        )


        binding.detailCloseIcon.setOnClickListener {
            findNavController().navigateUp()
        }
        viewModel.chartData.observe(viewLifecycleOwner, Observer {

            val candleEntries = it.toCandleEntry()

            chart.setChartData(candleEntries, it.lastIndex downTo it.lastIndex - 30)


        })
    }

    private fun chartConfig(chart: CandleStickChart) {
        chart.setBackgroundColor(Color.WHITE)

        chart.description.isEnabled = true

        chart.setMaxVisibleValueCount(10)

        chart.setPinchZoom(false)
        chart.isDoubleTapToZoomEnabled = false

        chart.setDrawGridBackground(false)
        chart.setDrawBorders(false)

        chart.legend.isEnabled = false

        chart.axisLeft.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.xAxis.isEnabled = false

        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val candle: Candle? = e?.let {
                    viewModel.getCandle(it.x)
                }

                candle?.let {

                    val period = candle.period

                    period?.let {
                        val date = Date(period)
                        chart.description.text = "$candle \n"
                        chart.description.text += "$date"

                    }

                }
            }

            override fun onNothingSelected() {
            }
        })
    }

    private fun CandleStickChart.setChartData(dataList: List<CandleEntry>, range: IntProgression) {

        val set = CandleDataSet(dataList.getRange(range), "data set")

        configDataSet(set)

        data = CandleData(set)

        invalidate()

    }

    private fun configDataSet(set: CandleDataSet) {
        set.setDrawIcons(false)
        set.axisDependency = AxisDependency.LEFT
        set.shadowWidth = 0.7f
        set.decreasingColor = Color.RED
        set.decreasingPaintStyle = Paint.Style.FILL
        set.increasingColor = Color.GREEN
        set.increasingPaintStyle = Paint.Style.FILL
        set.neutralColor = Color.BLUE
        set.shadowColorSameAsCandle = true


    }

    private fun List<Candle>.toCandleEntry() = mapIndexed { index, candle ->
        CandleEntry(
            index.toFloat(),
            candle.high?.toFloat() ?: 0F,
            candle.low?.toFloat() ?: 0F,
            candle.open?.toFloat() ?: 0F,
            candle.close?.toFloat() ?: 0F
        )
    }

    private fun <E> List<E>.getRange(range: IntProgression): MutableList<E> {
        val a = mutableListOf<E>()

        for ((i, j) in this.withIndex())
            if (i in range)
                a.add(j)

        return a
    }

    override fun onStop() {
        super.onStop()
        activity?.findViewById<BottomNavigationView>(R.id.main_bottom_navigation)?.visibility = View.VISIBLE
    }
}