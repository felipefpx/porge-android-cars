package me.porge.android.cars.vehicles.layers.presentation.binders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.item_vehicle.view.*
import me.porge.android.cars.core.layers.presentation.views.extensions.loadFromUrl
import me.porge.android.cars.core.layers.presentation.views.extensions.setHtmlText
import me.porge.android.cars.core.layers.presentation.views.listable.ListableBinder
import me.porge.android.cars.core.layers.presentation.views.listable.OnListableItemClicked
import me.porge.android.cars.vehicles.R
import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle

object VehicleBinder : ListableBinder<Vehicle> {
    override fun listableTypeIdentifier() = Vehicle::class.hashCode()

    override fun buildView(parent: ViewGroup): View {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_vehicle, parent, false)
    }

    override fun bind(rootView: View, item: Vehicle, onItemClicked: OnListableItemClicked?) {
        with(rootView) {
            setOnClickListener { onItemClicked?.invoke(item) }

            setBackgroundColor(
                when {
                    item.isSelected -> R.color.green
                    else -> android.R.color.white
                }.let { ContextCompat.getColor(context, it) }
            )

            textVehicleItemTitle.apply {
                text = context.getString(
                    R.string.vehicle_item_title,
                    item.modelName,
                    item.licensePlate
                )

                setTextColor(
                    when {
                        item.isSelected -> android.R.color.black
                        else -> R.color.green
                    }.let { ContextCompat.getColor(context, it) }
                )
            }

            textVehicleItemSubtitle.apply {
                setHtmlText(context.getString(R.string.vehicle_item_subtitles, item.name))
                setTextColor(
                    when {
                        item.isSelected -> android.R.color.black
                        else -> R.color.gray_dark
                    }.let { ContextCompat.getColor(context, it) }
                )
            }

            imageVehicleItemIcon.loadFromUrl(item.carImageUrl, R.drawable.ic_vehicle_placeholder)
        }
    }
}