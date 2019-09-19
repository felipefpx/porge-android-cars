package me.porge.android.cars.core.layers.presentation.views.extensions

import android.view.View

fun View.makeGoneIf(condition: Boolean) {
    visibility = if (condition) View.GONE else View.VISIBLE
}

fun View.makeInvisibleIf(condition: Boolean) {
    visibility = if (condition) View.INVISIBLE else View.VISIBLE
}

fun View.makeVisibleIf(condition: Boolean) {
    makeGoneIf(!condition)
}