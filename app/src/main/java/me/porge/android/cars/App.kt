package me.porge.android.cars

import android.app.Application
import me.porge.android.cars.core.di.coreArchModule
import me.porge.android.cars.vehicles.di.featVehiclesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(coreArchModule, featVehiclesModule))
        }
    }
}