package com.yasin.cryptooverview.adapters

import android.graphics.Color
import android.graphics.drawable.PictureDrawable
import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.yasin.cryptooverview.RequestStatus
import com.yasin.cryptooverview.svgRender.GlideApp
import com.yasin.cryptooverview.svgRender.SvgSoftwareLayerSetter

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
            Snackbar.make(this, "Connection Failed\nWe are on offline cache :)", 4000).show()
            View.GONE
        }
        RequestStatus.Complete -> View.GONE
    }
}

@BindingAdapter("refreshStatus")
fun TextView.bindRefreshText(status: RequestStatus) {
    visibility = when (status) {
        RequestStatus.Error -> View.VISIBLE
        else -> View.GONE
    }
}
