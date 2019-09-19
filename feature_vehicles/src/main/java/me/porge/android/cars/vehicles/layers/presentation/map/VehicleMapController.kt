package me.porge.android.cars.vehicles.layers.presentation.map

import androidx.annotation.VisibleForTesting
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle

class VehicleMapController(
    @VisibleForTesting val selectedMarkerIcon: BitmapDescriptor?,
    @VisibleForTesting val normalMarkerIcon: BitmapDescriptor?,
    private val googleMap: GoogleMap,
    private val onVehicleSelected: (Vehicle) -> Unit
) : GoogleMap.OnMarkerClickListener {

    @VisibleForTesting
    var selectedMarker: Marker? = null

    @VisibleForTesting
    val markers = mutableMapOf<Vehicle, Marker>()

    init {
        googleMap.prepare()
    }

    @VisibleForTesting
    fun GoogleMap.prepare() {
        uiSettings.apply {
            isZoomControlsEnabled = false
            isZoomGesturesEnabled = true
            isRotateGesturesEnabled = true
            isCompassEnabled = false
        }

        setOnMarkerClickListener(this@VehicleMapController)
    }

    fun setVehicles(vehicles: List<Vehicle>) {
        with(googleMap) {
            // Remove old markers
            markers.clear()
            clear()

            vehicles
                .map { vehicle -> vehicle to addVehicleMarker(vehicle) }
                .let { markers.putAll(it) }

            if (vehicles.isNotEmpty()) {
                LatLngBounds
                    .Builder()
                    .apply {
                        markers
                            .values
                            .map { it.position }
                            .map { LatLng(it.latitude, it.longitude) }
                            .forEach { include(it) }
                    }
                    .build()
                    .let {
                        animateCamera(
                            CameraUpdateFactory.newLatLngBounds(it, DEFAULT_BOUNDS_PADDING)
                        )
                    }
            }
        }
    }

    fun focusOnVehicle(vehicle: Vehicle) {
        onMarkerClick(markers[vehicle] ?: googleMap.addVehicleMarker(vehicle))
    }

    @VisibleForTesting
    override fun onMarkerClick(marker: Marker?): Boolean {
        marker ?: return false

        with(googleMap) {
            markers
                .entries
                .firstOrNull { it.value == marker }
                ?.let { onVehicleSelected(it.key) }

            selectedMarker?.setIcon(normalMarkerIcon)
            selectedMarker = marker.apply { setIcon(selectedMarkerIcon) }

            CameraUpdateFactory
                .newLatLngZoom(marker.position, DEFAULT_SELECTED_ZOOM)
                .let { animateCamera(it) }
        }
        return true
    }

    @VisibleForTesting
    fun GoogleMap.addVehicleMarker(vehicle: Vehicle): Marker {
        return MarkerOptions()
            .position(LatLng(vehicle.latitude, vehicle.longitude))
            .icon(normalMarkerIcon)
            .draggable(false)
            .let(::addMarker)
    }

    companion object {
        @VisibleForTesting
        const val DEFAULT_BOUNDS_PADDING = 24

        @VisibleForTesting
        const val DEFAULT_SELECTED_ZOOM = 18.0f
    }
}