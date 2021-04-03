package com.yasin.cryptooverview.destinations

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.yasin.cryptooverview.R
import com.yasin.cryptooverview.getRange
import com.yasin.cryptooverview.models.Candle
import com.yasin.cryptooverview.toCandleEntry
import java.util.*

class ChartFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var chart: CandleStickChart
    private val args: ChartFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_chart, container, false)

        //initialize chart
        chart = rootView.findViewById(R.id.chart)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chartConfig(chart)
        args.Candels.toList().toCandleEntry()?.let {
            chart.setChartData(it)
        }


    }

    private fun chartConfig(chart: CandleStickChart) {
        chart.setBackgroundColor(resources.getColor(R.color.MainBackground))

        chart.description.isEnabled = true

        chart.setMaxVisibleValueCount(10)

        chart.setPinchZoom(false)
        chart.isDoubleTapToZoomEnabled = true
        chart.setScaleEnabled(true)


        chart.setDrawGridBackground(false)
        chart.setDrawBorders(false)

        chart.legend.isEnabled = false

        chart.axisLeft.isEnabled = false
        chart.axisRight.isEnabled = true
        chart.xAxis.isEnabled = true

        chart.axisRight.textColor = resources.getColor(R.color.colorOnPrimary)

        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val candle: Candle? = e?.let {
                    args.Candels[it.x.toInt()]
                }

                candle?.let {
                    val txt = rootView.findViewById<TextView>(R.id.chart_tv_detail)
                    val period = candle.period

                    period?.let {
                        val date = Date(period)
                        txt.text = String.format(
                            "open : %.2f \nhigh : %.2f \nlow : %.2f \nclose : %.2f",
                            candle.open?.toFloat(),
                            candle.high?.toFloat(),
                            candle.low?.toFloat(),
                            candle.close?.toFloat(),
                        )


                    }

                }
            }

            override fun onNothingSelected() {
            }
        })
    }

    private fun CandleStickChart.setChartData(
        dataList: List<CandleEntry>,
        range: IntProgression? = null
    ) {

        val set =
            CandleDataSet(if (range == null) dataList else dataList.getRange(range), "data set")

        configDataSet(set)

        data = CandleData(set)

        invalidate()

    }

    private fun configDataSet(set: CandleDataSet) {
        set.setDrawIcons(false)
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.shadowWidth = 0.7f
        set.decreasingColor = Color.RED
        set.decreasingPaintStyle = Paint.Style.FILL
        set.increasingColor = Color.GREEN
        set.increasingPaintStyle = Paint.Style.FILL
        set.neutralColor = Color.BLUE
        set.shadowColorSameAsCandle = true


    }

}