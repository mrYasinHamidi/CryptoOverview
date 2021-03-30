package com.yasin.cryptooverview.adapters

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.graphics.drawable.PictureDrawable
import android.opengl.Visibility
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.yasin.cryptooverview.ChartDataInterval
import com.yasin.cryptooverview.R
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.svgRender.GlideApp
import com.yasin.cryptooverview.svgRender.SvgSoftwareLayerSetter
import com.yasin.cryptooverview.viewModels.DetailViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import org.joda.time.DateTime
import org.joda.time.Interval
import java.util.*

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    if (!imgUrl.isNullOrEmpty())

        if (imgUrl.endsWith("svg"))

            GlideApp.with(imgView.context)
                .`as`(PictureDrawable::class.java)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(SvgSoftwareLayerSetter())
                .miniThumb()
                .load(imgUrl)
                .into(imgView)
        else

            GlideApp.with(imgView.context)
                .load(imgUrl)
                .miniThumb()
                .into(imgView)


}

@BindingAdapter("setColoredPrice")
fun TextView.bindText(value: String) {
    if (value.isNotEmpty()) {

        val price = value.toDouble()

        if (price > 0) {
            setTextColor(Color.GREEN)
        } else if (price < 0) {
            setTextColor(Color.RED)
        }
        text = value
    }
}

@BindingAdapter("progressStatus")
fun ProgressBar.bindProgressBar(status: RequestStatus) {
    visibility = when (status) {
        RequestStatus.Loading -> View.VISIBLE
        RequestStatus.Error -> {
            try {
                Toast.makeText(
                    this.context,
                    "Something went wrong!!!",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
            }
            View.GONE
        }
        RequestStatus.Complete -> {
            context.getSharedPreferences("market", MODE_PRIVATE).edit()
                .putLong("time", Calendar.getInstance().time.time).apply()
            View.GONE
        }
    }
}


@BindingAdapter("refreshStatus")
fun TextView.bindRefreshText(status: RequestStatus) {
    visibility = when (status) {
        RequestStatus.Error -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("setLastUpdateText")
fun TextView.bindLastUpdateText(status: RequestStatus) {
    visibility = when (status) {
        RequestStatus.Error -> {
            val p = context.getSharedPreferences("market", MODE_PRIVATE)
            val lstUpdate = DateTime(Date(p.getLong("time", 0)))
            val currentTime = DateTime(Calendar.getInstance().time)
            text = String.format(
                context.resources.getString(R.string.refresh_text),
                getLastUpdate(lstUpdate,currentTime)
            )

            View.VISIBLE
        }
        else -> View.GONE
    }
}

@BindingAdapter("chartProgressStatus")
fun ProgressBar.chartBindProgressBar(status: RequestStatus) {
    visibility = when (status) {
        RequestStatus.Loading -> View.VISIBLE
        RequestStatus.Error -> View.GONE
        RequestStatus.Complete -> View.GONE
    }
}

@BindingAdapter("chartStatus")
fun View.bindChart(status: RequestStatus) {
    visibility = when (status) {
        RequestStatus.Loading -> View.GONE
        RequestStatus.Error -> View.VISIBLE
        RequestStatus.Complete -> View.VISIBLE
    }
}


fun getLastUpdate(start: DateTime, end: DateTime): String {
    when {
        end.year().get() - start.year().get() > 0 -> {
            return "${end.year().get() - start.year().get()} year ago"

        }
        end.monthOfYear().get() - start.monthOfYear().get() > 0 -> {
            return "${end.monthOfYear().get() - start.monthOfYear().get()} month ago"

        }
        else -> {
            val duration = Interval(start, end).toDuration()
            return when {
                duration.standardDays > 0 -> {
                    "${duration.standardDays} day ago"
                }
                duration.standardHours > 0 -> {
                    "${duration.standardHours} hour ago"
                }
                duration.standardMinutes > 0 -> {
                    "${duration.standardMinutes} minute ago"
                }
                else -> {
                    "moments ago"
                }
            }
        }
    }

}



















