package me.porge.android.cars.vehicles.layers.data.remote

import me.porge.android.cars.vehicles.layers.domain.entities.Vehicle
import retrofit2.http.GET

interface VehiclesRemoteSource {
    @GET("cars")
    suspend fun getVehicles(): List<Vehicle>
}