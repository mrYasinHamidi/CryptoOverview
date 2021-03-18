package com.yasin.cryptooverview.adapters

import android.graphics.drawable.PictureDrawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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

@BindingAdapter("layoutFullscreen")
fun View.bindLayoutFullscreen(previousFullscreen: Boolean, fullscreen: Boolean) {
    if (previousFullscreen != fullscreen && fullscreen) {
        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}