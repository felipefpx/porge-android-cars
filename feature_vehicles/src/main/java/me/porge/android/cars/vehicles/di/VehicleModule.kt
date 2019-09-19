package me.porge.android.cars.vehicles.di

import me.porge.android.cars.core.layers.data.BASE_API_URL
import me.porge.android.cars.vehicles.layers.data.VehiclesRepository
import me.porge.android.cars.vehicles.layers.data.remote.VehiclesRemoteSource
import me.porge.android.cars.vehicles.layers.domain.VehiclesUseCases
import me.porge.android.cars.vehicles.layers.presentation.VehiclesViewModel
import me.porge.android.cars.vehicles.layers.presentation.binders.VehicleBinder
import me.porge.android.cars.vehicles.layers.presentation.map.VehicleMapController
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val VEHICLE_BINDERS = "vehicleListableBinders"

val featVehiclesModule = module {
    single {
        get<Retrofit> { parametersOf(BASE_API_URL) }
            .create(VehiclesRemoteSource::class.java)
    }

    single<VehiclesUseCases> {
        VehiclesRepository(get(), get())
    }

    viewModel {
        VehiclesViewModel(get())
    }

    single(named(VEHICLE_BINDERS)) {
        mapOf(
            VehicleBinder.listableTypeIdentifier() to VehicleBinder
        )
    }

    factory { parameters ->
        VehicleMapController(
            selectedMarkerIcon = parameters.component1(),
            normalMarkerIcon = parameters.component2(),
            googleMap = parameters.component3(),
            onVehicleSelected = parameters.component4()
        )
    }
}