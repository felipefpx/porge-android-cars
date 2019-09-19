package me.porge.android.cars.core.layers.presentation.views.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import me.porge.android.cars.core.di.KoinInjector
import me.porge.android.cars.core.layers.presentation.views.imageloader.ImageLoader
import org.koin.core.get

fun ImageView.loadFromUrl(url: String?, @DrawableRes placeholderResId: Int) {
    KoinInjector
        .get<ImageLoader>()
        .loadImageFromUrl(this, url, placeholderResId)
}