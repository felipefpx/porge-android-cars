package me.porge.android.cars.core.layers.presentation.views.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

open class ImageLoader {
    open fun loadImageFromUrl(
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderResId: Int
    ) {
        Glide
            .with(imageView.context)
            .load(url)
            .centerCrop()
            .placeholder(placeholderResId)
            .into(imageView)
    }
}