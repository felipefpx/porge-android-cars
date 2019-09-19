package me.porge.android.cars.tests.unit.extensions

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Starts koin by overriding some providers if needed.
 */
fun startKoinForTests(modules: List<Module> = emptyList(), overrideBlock: Module.() -> Unit = {}) {
    startKoin {
        loadKoinModules(
            modules.toMutableList().apply { add(module(override = true) { overrideBlock() }) }
        )
    }
}