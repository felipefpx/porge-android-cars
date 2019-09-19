package me.porge.android.cars.vehicles.layers.presentation.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.scrollToTop() {
    postDelayed({ smoothScrollToPosition(0) }, 200)
}