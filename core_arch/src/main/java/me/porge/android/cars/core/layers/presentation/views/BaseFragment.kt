package me.porge.android.cars.core.layers.presentation.views

import androidx.fragment.app.Fragment
import me.porge.android.cars.core.layers.presentation.viewmodels.BaseViewModel
import me.porge.android.cars.core.layers.presentation.viewstates.BaseViewState

abstract class BaseFragment<ViewState : BaseViewState, ViewModel : BaseViewModel<ViewState>> :
    Fragment(), BaseView<ViewState> {

    abstract val viewModel: ViewModel
}