package me.porge.android.cars.vehicles.layers.data

import kotlinx.coroutines.withContext
import me.porge.android.cars.core.DispatcherProvider
import me.porge.android.cars.vehicles.layers.data.remote.VehiclesRemoteSource
import me.porge.android.cars.vehicles.layers.domain.VehiclesUseCases

class VehiclesRepository(
    private val dispatcherProvider: DispatcherProvider,
    private val vehiclesRemoteSource: VehiclesRemoteSource
) : VehiclesUseCases {

    override suspend fun getVehicles() =
        withContext(dispatcherProvider.io) { vehiclesRemoteSource.getVehicles() }
}