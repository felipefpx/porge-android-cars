package me.porge.android.cars.vehicles.layers.domain

import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle

interface VehiclesUseCases {
    suspend fun getVehicles(): List<Vehicle>
}