package me.porge.android.cars.vehicles.layers.presentation

import androidx.annotation.StringRes
import me.porge.android.cars.core.layers.presentation.viewstates.BaseViewState
import me.porge.android.cars.vehicles.R
import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle

sealed class VehiclesViewState(
    val vehicles: List<Vehicle> = emptyList(),
    val isLoading: Boolean = false
) : BaseViewState {

    object Empty : VehiclesViewState()

    class Loading(
        vehicles: List<Vehicle> = emptyList()
    ) : VehiclesViewState(vehicles, true)

    class VehicleFocused(
        vehicles: List<Vehicle>,
        val selectedVehicle: Vehicle
    ) : VehiclesViewState(vehicles)

    class Vehicles(
        vehicles: List<Vehicle>
    ) : VehiclesViewState(vehicles)

    class Error(
        @StringRes val errorMsgResId: Int = R.string.vehicles_error,
        @StringRes val errorPositiveActionResId: Int = R.string.vehicles_error_action
    ) : VehiclesViewState()
}