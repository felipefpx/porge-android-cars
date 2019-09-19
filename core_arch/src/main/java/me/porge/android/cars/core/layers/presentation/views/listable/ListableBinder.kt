package me.porge.android.cars.core.layers.presentation.views.listable

import android.view.View
import android.view.ViewGroup

interface ListableBinder<T : Listable> {
    fun listableTypeIdentifier(): Int

    fun buildView(parent: ViewGroup): View

    fun bind(
        rootView: View,
        item: T,
        onItemClicked: OnListableItemClicked?
    )

    @Suppress("UNCHECKED_CAST")
    fun safeBind(
        rootView: View,
        item: Listable,
        onItemClicked: OnListableItemClicked?
    ) {
        (item as? T)?.let { bind(rootView, it, onItemClicked) }
    }
}