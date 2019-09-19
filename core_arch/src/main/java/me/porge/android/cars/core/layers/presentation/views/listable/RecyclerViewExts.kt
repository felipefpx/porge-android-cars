package me.porge.android.cars.core.layers.presentation.views.listable

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.porge.android.cars.core.di.KoinInjector
import org.koin.core.get
import org.koin.core.parameter.parametersOf

typealias OnListableItemClicked = (Listable) -> Unit

val RecyclerView.listableAdapter
    get() = adapter as? ListableRvAdapter


fun RecyclerView.attachListableAdapter(
    binders: Map<Int, ListableBinder<*>>,
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    onItemClicked: OnListableItemClicked? = null
) {
    layoutManager = manager
    adapter = KoinInjector.get<ListableRvAdapter> { parametersOf(binders, onItemClicked) }
}