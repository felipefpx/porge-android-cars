package me.porge.android.cars.core.layers.presentation.views.listable

interface Listable {
    fun typeIdentifier(): Int
    fun isSameItem(otherListable: Listable?): Boolean
}