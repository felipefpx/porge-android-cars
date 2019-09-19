package me.porge.android.cars.core.layers.presentation.views

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import me.porge.android.cars.core.layers.presentation.viewstates.BaseViewState

interface BaseView<ViewState : BaseViewState> : LifecycleOwner {
    fun bind(viewState: ViewState)

    fun LiveData<ViewState>.observeViewState() =
        Observer<ViewState> { it?.let(::bind) }.also { observe(this@BaseView, it) }
}