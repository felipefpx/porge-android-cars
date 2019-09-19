package me.porge.android.cars.core

import kotlinx.coroutines.Dispatchers

open class DispatcherProvider {
    open val io = Dispatchers.IO
    open val default = Dispatchers.Default
    open val unconfined = Dispatchers.Unconfined
}