package me.porge.android.cars.tests.unit.extensions

import androidx.lifecycle.LiveData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun <T> LiveData<T>.blockingObserve(): T? {
    var result: T? = null
    val countDownLatch = CountDownLatch(1)

    observeForever { newValue ->
        result = newValue
        countDownLatch.countDown()
    }

    countDownLatch.await(300, TimeUnit.MILLISECONDS)
    return result
}