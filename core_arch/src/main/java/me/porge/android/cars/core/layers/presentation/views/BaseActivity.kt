package me.porge.android.cars.core.layers.presentation.views

import androidx.appcompat.app.AppCompatActivity
import me.porge.android.cars.core.layers.presentation.viewmodels.BaseViewModel
import me.porge.android.cars.core.layers.presentation.viewstates.BaseViewState

abstract class BaseActivity<ViewState : BaseViewState, ViewModel : BaseViewModel<ViewState>> :
    AppCompatActivity(), BaseView<ViewState> {

    abstract val viewModel: ViewModel
}