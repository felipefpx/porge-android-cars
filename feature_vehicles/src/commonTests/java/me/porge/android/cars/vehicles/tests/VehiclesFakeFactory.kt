package me.porge.android.cars.vehicles.tests

import me.porge.android.cars.vehicles.layers.domain.VehiclesUseCases
import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle

fun createFakeVehicle(id: Int = 0) =
    Vehicle(
        id = id.toString(),
        modelName = "Vehicle $id",
        name = "Driver $id",
        licensePlate = "M-XXXXXX$id",
        latitude = id * 0.1,
        longitude = id * 0.2,
        carImageUrl = "http://vehicle.com/logo$id.png",
        isSelected = false
    )

object FakeVehicleUseCases : VehiclesUseCases {
    override suspend fun getVehicles(): List<Vehicle> = listOf(createFakeVehicle(1))
}