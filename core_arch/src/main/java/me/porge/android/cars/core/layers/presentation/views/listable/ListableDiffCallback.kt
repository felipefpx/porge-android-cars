package me.porge.android.cars.core.layers.presentation.views.listable

import androidx.recyclerview.widget.DiffUtil

class ListableDiffCallback(
    val oldList: List<Listable>,
    val newList: List<Listable>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList
            .getOrNull(oldItemPosition)
            ?.isSameItem(newList.getOrNull(newItemPosition)) == true
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList.getOrNull(oldItemPosition) ?: return false
        val newItem = newList.getOrNull(newItemPosition) ?: return false
        return oldItem == newItem
    }
}