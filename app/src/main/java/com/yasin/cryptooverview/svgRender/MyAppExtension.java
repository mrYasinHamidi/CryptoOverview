package com.yasin.cryptooverview.svgRender;

import androidx.annotation.NonNull;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.BaseRequestOptions;
import com.yasin.cryptooverview.R;

@GlideExtension
public class MyAppExtension {
    // Size of mini thumb in pixels.
    private static final int MINI_THUMB_SIZE = 100;

    private MyAppExtension() {
    } // utility class

    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> miniThumb(BaseRequestOptions<?> options) {
        return options
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image);
    }

}