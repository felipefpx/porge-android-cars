package me.porge.android.cars.tests.instrumented.koin

import android.app.Application
import me.porge.android.cars.core.di.coreArchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinInstrumentedTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinInstrumentedTestApp)
            modules(listOf(coreArchModule, androidTestModule))
        }
    }
}