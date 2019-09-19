package me.porge.android.cars.tests.fakes

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import io.mockk.mockk
import me.porge.android.cars.core.layers.presentation.viewmodels.BaseViewModel
import me.porge.android.cars.core.layers.presentation.views.BaseView
import me.porge.android.cars.core.layers.presentation.views.listable.Listable
import me.porge.android.cars.core.layers.presentation.views.listable.ListableBinder
import me.porge.android.cars.core.layers.presentation.views.listable.OnListableItemClicked
import me.porge.android.cars.core.layers.presentation.viewstates.BaseViewState

fun createFakeViewModel() = FakeViewModel()

class FakeViewModel : BaseViewModel<BaseViewState>() {
    override val defaultViewState = FakeDefaultViewState
}

object FakeViewState : BaseViewState
object FakeDefaultViewState : BaseViewState

object FakeView : BaseView<BaseViewState> {
    override fun getLifecycle(): Lifecycle {
        return mockk()
    }

    override fun bind(viewState: BaseViewState) {
    }
}

object FakeListable : Listable {
    override fun typeIdentifier(): Int = 0
    override fun isSameItem(otherListable: Listable?): Boolean = this === otherListable
}

class FakeListableBinder(private val view: View) : ListableBinder<FakeListable> {
    override fun listableTypeIdentifier(): Int = FakeListable.typeIdentifier()

    override fun buildView(parent: ViewGroup): View = view

    override fun bind(rootView: View, item: FakeListable, onItemClicked: OnListableItemClicked?) {
    }
}