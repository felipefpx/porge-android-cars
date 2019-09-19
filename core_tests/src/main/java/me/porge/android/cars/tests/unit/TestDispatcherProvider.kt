package me.porge.android.cars.tests.unit

import kotlinx.coroutines.CoroutineDispatcher
import me.porge.android.cars.core.DispatcherProvider

class TestDispatcherProvider(
    val dispatcher: CoroutineDispatcher
) : DispatcherProvider() {
    override val io = dispatcher
    override val default = dispatcher
    override val unconfined = dispatcher
}