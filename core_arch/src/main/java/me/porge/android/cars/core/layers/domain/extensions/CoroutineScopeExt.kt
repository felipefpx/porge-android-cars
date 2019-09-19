package me.porge.android.cars.core.layers.domain.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T> CoroutineScope.launchRequest(
    requestBlock: suspend () -> T,
    onSuccess: (T) -> Unit,
    onFailure: (Throwable) -> Unit
) = launch {
    try {
        onSuccess(requestBlock())
    } catch (error: Throwable) {
        onFailure(error)
    }
}