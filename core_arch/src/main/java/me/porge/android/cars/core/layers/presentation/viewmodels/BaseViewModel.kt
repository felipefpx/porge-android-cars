package me.porge.android.cars.core.layers.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.porge.android.cars.core.layers.presentation.viewstates.BaseViewState

abstract class BaseViewModel<ViewState : BaseViewState> : ViewModel() {

    abstract val defaultViewState: ViewState

    private val viewStateLiveData by lazy {
        MutableLiveData<ViewState>().apply { postValue(defaultViewState) }
    }

    fun getLiveViewState(): LiveData<ViewState> = viewStateLiveData

    fun setViewState(viewState: ViewState) {
        viewStateLiveData.postValue(viewState)
    }

    open fun onResetViewState() {
        setViewState(defaultViewState)
    }
}