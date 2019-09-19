package me.porge.android.cars.core.layers.presentation.views.listable

import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class ListableRvAdapter(
    private val binders: Map<Int, ListableBinder<*>>,
    var onItemClicked: OnListableItemClicked? = null
) : RecyclerView.Adapter<ListableRvAdapter.ViewHolder>() {

    @VisibleForTesting
    val data = mutableListOf<Listable>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            binders[viewType]?.buildView(parent) ?: View(parent.context)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data.getOrNull(position) ?: return

        binders[holder.itemViewType]
            ?.safeBind(holder.rootView, item, onItemClicked)
    }

    override fun getItemViewType(position: Int): Int {
        return data.getOrNull(position)?.typeIdentifier() ?: -1
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setItems(items: List<Listable>) {
        val oldData = data.toList()

        data.apply {
            clear()
            addAll(items)
        }

        val diffCallback = ListableDiffCallback(oldList = oldData, newList = data)

        DiffUtil
            .calculateDiff(diffCallback, true)
            .dispatchUpdatesTo(this)
    }

    class ViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView)
}