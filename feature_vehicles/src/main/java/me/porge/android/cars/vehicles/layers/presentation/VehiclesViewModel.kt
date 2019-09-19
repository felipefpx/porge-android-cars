package me.porge.android.cars.vehicles.layers.presentation

import androidx.lifecycle.viewModelScope
import me.porge.android.cars.core.layers.domain.extensions.launchRequest
import me.porge.android.cars.core.layers.presentation.viewmodels.BaseViewModel
import me.porge.android.cars.vehicles.layers.domain.VehiclesUseCases
import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle

class VehiclesViewModel(
    private val vehiclesUseCases: VehiclesUseCases
) : BaseViewModel<VehiclesViewState>() {

    override val defaultViewState = VehiclesViewState.Empty

    fun onMapReady() {
        VehiclesViewState
            .Loading(getLiveViewState().value?.vehicles ?: emptyList())
            .let(::setViewState)

        viewModelScope.launchRequest(
            requestBlock = vehiclesUseCases::getVehicles,
            onSuccess = { result ->
                when {
                    result.isEmpty() -> VehiclesViewState.Empty
                    else -> VehiclesViewState.Vehicles(result)
                }.let(::setViewState)
            },
            onFailure = {
                VehiclesViewState.Error().let(::setViewState)
            }
        )
    }

    fun onVehicleSelected(vehicle: Vehicle) {
        val vehicleList = getLiveViewState().value?.vehicles

        if (vehicleList?.any { it.id == vehicle.id } != true) return

        val selectedVehicle = vehicle.copy(isSelected = true)
        val updatedList =
            vehicleList
                .map { it.copy(isSelected = it.id == vehicle.id) }
                .sortedByDescending { it.isSelected }

        VehiclesViewState
            .VehicleFocused(vehicles = updatedList, selectedVehicle = selectedVehicle)
            .let(::setViewState)
    }
}