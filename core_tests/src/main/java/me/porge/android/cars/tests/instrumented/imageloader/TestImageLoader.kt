package me.porge.android.cars.tests.instrumented.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import me.porge.android.cars.R
import me.porge.android.cars.core.layers.presentation.views.imageloader.ImageLoader

class TestImageLoader(
    @DrawableRes var successDrawableResId: Int = R.drawable.ic_porge_logo,
    var successUrl: String = ""
) : ImageLoader() {
    override fun loadImageFromUrl(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderResId: Int
    ) {
        imageView.setImageResource(
            when (successUrl) {
                url -> successDrawableResId
                else -> placeholderResId
            }
        )
    }
}