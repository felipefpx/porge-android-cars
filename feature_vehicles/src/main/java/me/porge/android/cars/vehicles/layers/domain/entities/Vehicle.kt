package me.porge.android.cars.vehicles.layers.domain.entities

import com.google.gson.annotations.SerializedName
import me.porge.android.cars.core.layers.presentation.views.listable.Listable

data class Vehicle(
    @SerializedName("id") val id: String,
    @SerializedName("modelName") val modelName: String,
    @SerializedName("name") val name: String,
    @SerializedName("licensePlate") val licensePlate: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("carImageUrl") val carImageUrl: String? = null,
    val isSelected: Boolean = false
) : Listable {
    override fun typeIdentifier() = Vehicle::class.hashCode()

    override fun isSameItem(otherListable: Listable?) =
        (otherListable as? Vehicle)?.id == id
}
